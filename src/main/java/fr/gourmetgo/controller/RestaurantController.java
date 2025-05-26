package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
public class RestaurantController {

    @Autowired 
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String accueilGR() {
        return "accueilGR"; 
    }

    // @GetMapping("/dashboard")
    // public String connexionOK() {
    //     return "dashboard"; 
    // }


//--------------------------------------------------------------------------------------------
/* ------------------ Partie Adam (formulaire) ------------------ */
//--------------------------------------------------------------------------------------------

           /* ------------------ Partie inscription Restaurant ------------------ */
    /**
     * Affiche le formulaire d'inscription d'un restaurant.
     * <p>
     * Cette méthode gère la requête GET pour afficher le formulaire d'inscription.
     * Elle crée une nouvelle instance de {@code Restaurant} et réinitialise le type de restaurant à une chaîne vide
       (afin d'afficher le placeholder dans le menu déroulant, et ajouter l'objet au modèle.)
     * </p>
     *
     * @param model le modèle dans lequel l'instance du restaurant est ajoutée afin d'être liée au formulaire
     * @return le nom de la vue Thymeleaf correspondant au formulaire ("formulaire")
     */
    @GetMapping("/restaurants/formulaire")
    public String afficherFormulaire(Model model) {
        Restaurant restaurant = new Restaurant(); // Création d'une nouvelle instance de Restaurant
        restaurant.setTypeResto("");                // Initialisation du type de restaurant à vide
        model.addAttribute("restaurant", restaurant);
        return "formulaire";
    }

/*---------------------------------------------------------------------------------------------------------------------------------- */
    /**
     * Traite la soumission du formulaire d'inscription d'un restaurant.
     * <p>
     * Cette méthode gère la requête POST envoyée depuis le formulaire. Elle effectue plusieurs traitements :
     
    *     Gestion de l'image : vérifie si un fichier a été envoyé. Si c'est le cas, l'image est uploadée dans le dossier "uploads"
    *     et le chemin du fichier est stocké dans l'objet {@code Restaurant}. En cas d'erreur lors de l'upload, la méthode ajoute 
    *     un message d'erreur au modèle et réaffiche le formulaire.

    *     Validation globale (champs) : après la gestion de l'image, l'objet {@code Restaurant} est validé automatiquement grâce à l'annotation {@code @Valid}
    *     et aux contraintes définies dans la classe. Si des erreurs sont présentes, le formulaire est réaffiché.
    
    *     Vérification d'existence : la méthode vérifie ensuite si un restaurant similaire existe déjà. Si c'est le cas, une erreur est ajoutée et
    *     le formulaire est réaffiché.
    
    *     Sauvegarde : si aucune erreur n'est détectée, le restaurant est sauvegardé et la méthode redirige vers la page succès en passant l'identifiant
    *     du nouveau restaurant en paramètre.

    * </p>
    *
    * @param restaurant L'objet {@code Restaurant} rempli avec les données du formulaire.
    * @param result Le résultat de la validation qui peut contenir des erreurs si certaines contraintes ne sont pas respectées.
    * @param imageFile Le fichier image envoyé avec le formulaire. Il est utilisé pour effectuer l'opération d'upload.
    * @param model Le modèle qui permet de renvoyer des messages d'erreur vers la vue en cas de problème.
    * @return Si une erreur est détectée, retourne "formulaire" pour réafficher le formulaire.
    *         Sinon, redirige vers "/admin/success" avec l'ID du restaurant nouvellement enregistré.
    */
    @PostMapping("/formulaire")
    public String inscrireRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                    BindingResult result,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    Model model) {
        // Gestion de l'image
        if (!imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                restaurant.setImageResto("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("uploadError", "Erreur lors de l'envoi de l'image.");
                return "formulaire";
            }
        } else {
            result.rejectValue("imageResto", "imageFile.empty", "Veuillez ajouter une image.");
        }

        // Validation des autres champs via Bean Validation
        if (result.hasErrors()) {
            return "formulaire";
        }

        // Vérification de l'existence d'un restaurant similaire
        if (restaurantService.restaurantExists(restaurant)) {
            result.reject("duplicate", "Ce restaurant existe déjà.");
            return "formulaire";
        }

        // Sauvegarde du restaurant et redirection vers la page succès
        Restaurant nouveauRestaurant = restaurantService.enregistrerRestaurant(restaurant);
        return "redirect:/admin/success?restaurantId=" + nouveauRestaurant.getId();
    }
/*---------------------------------------------------------------------------------------------------------------------------------- */
              /* ------------------ Partie affichage de la page success ------------------ */

    /** n                 
     * Affiche la page succès après l'inscription du restaurant.
     * <p>
     * Cette méthode gère la requête GET vers la page de succès,
     * récupérant l'ID du restaurant à partir du paramètre de requête et ajoutant l'objet Restaurant correspondant au modèle 
     * pour affichage dans la vue.
     * </p>
     *
     * @param restaurantId l'identifiant du restaurant qui vient d'être inscrit
     * @param model le modèle dans lequel l'objet Restaurant est placé pour être affiché dans la vue
     * @return le nom de la vue Thymeleaf correspondant à la page succès ("success")
     */
    @GetMapping("/success")
    public String afficherSuccess(@RequestParam("restaurantId") Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "success";
    }
//--------------------------------------------------------------------------------------------
/* ------------------ FIN Partie Adam (formulaire) ------------------ */
//--------------------------------------------------------------------------------------------


    
    @GetMapping("/list")
    public String listRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "liste_restaurant";
    }

    @GetMapping("/list/{id}")
    public String showFicheRestaurant(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "fiche_restaurant";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "edit_restaurant";
    }

    @PostMapping("/restaurants/update")
    public String updateRestaurant(
        @ModelAttribute("restaurant") Restaurant restaurant,
        @RequestParam("imageFile") MultipartFile imageFile) {

    // Charger le restaurant existant
    Restaurant restaurantEnBase = restaurantService.getRestaurantById(restaurant.getId());

    if (!imageFile.isEmpty()) {
        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Mettre à jour le chemin de l'image dans l'objet temporaire
            restaurant.setImageResto("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            // Tu peux ajouter un message d’erreur ici si tu veux
        }
    } else {
        // Si pas de nouvelle image, garde l’ancienne dans l’objet à envoyer au service
        restaurant.setImageResto(restaurantEnBase.getImageResto());
    }

    restaurantService.updateRestaurant(restaurant);

    return "redirect:/admin/list/" + restaurant.getId();
}







    



}





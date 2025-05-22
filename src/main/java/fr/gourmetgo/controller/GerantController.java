package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.GerantRepository;

@Controller
public class GerantController {

    @Autowired
    private GerantRepository gerantRepository; // Assurez-vous que le nom de la variable correspond au type

    @PostMapping("/gerant-login") // Assurez-vous que le chemin de mappage est cohérent
    public String gerantLogin(@RequestParam String gerantEmail, @RequestParam String gerantMotDePasse) {
        Gerant gerant = new Gerant();
        gerant.setEmail(gerantEmail); // Utilisez les noms de variables corrects
        gerant.setMotDePasse(gerantMotDePasse); // Utilisez les noms de variables corrects
        gerant.setNom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        gerant.setPrenom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        gerantRepository.save(gerant); // Sauvegarde le client dans la base de données
        return "redirect:/dashboard";
    }
}

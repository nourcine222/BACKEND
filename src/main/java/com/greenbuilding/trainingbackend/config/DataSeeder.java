package com.greenbuilding.trainingbackend.config;

import com.greenbuilding.trainingbackend.entity.AppUser;
import com.greenbuilding.trainingbackend.entity.Domaine;
import com.greenbuilding.trainingbackend.entity.Employeur;
import com.greenbuilding.trainingbackend.entity.Profil;
import com.greenbuilding.trainingbackend.entity.Role;
import com.greenbuilding.trainingbackend.entity.RoleName;
import com.greenbuilding.trainingbackend.entity.Structure;
import com.greenbuilding.trainingbackend.repository.DomaineRepository;
import com.greenbuilding.trainingbackend.repository.EmployeurRepository;
import com.greenbuilding.trainingbackend.repository.ProfilRepository;
import com.greenbuilding.trainingbackend.repository.RoleRepository;
import com.greenbuilding.trainingbackend.repository.StructureRepository;
import com.greenbuilding.trainingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Inserts the starter data the first time the application runs.
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DomaineRepository domaineRepository;
    private final ProfilRepository profilRepository;
    private final StructureRepository structureRepository;
    private final EmployeurRepository employeurRepository;

    @Override
    public void run(String... args) {
        // Create the baseline roles and sample reference data if they do not already exist.
        Role simpleUser = ensureRole(RoleName.SIMPLE_USER);
        Role responsable = ensureRole(RoleName.RESPONSABLE);
        Role adminRole = ensureRole(RoleName.ADMINISTRATEUR);

        if (userRepository.findByLogin("admin").isEmpty()) {
            ensureUser("admin", "admin123", adminRole);
        }

        if (userRepository.findByLogin("responsable").isEmpty()) {
            ensureUser("responsable", "responsable123", responsable);
        }

        if (userRepository.findByLogin("user").isEmpty()) {
            ensureUser("user", "user123", simpleUser);
        }

        ensureDomaine("Informatique");
        ensureDomaine("Finance");
        ensureDomaine("Mecanique");

        ensureProfil("Informaticien bac + 5");
        ensureProfil("Gestionnaire");
        ensureProfil("Technicien superieur");

        ensureStructure("Direction centrale");
        ensureStructure("Direction regionale");

        ensureEmployeur("Green Building");
    }

    private Role ensureRole(RoleName name) {
        // Reuse the role if it already exists to keep startup idempotent.
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
    }

    private void ensureUser(String login, String rawPassword, Role role) {
        userRepository.save(
                AppUser.builder()
                        .login(login)
                        .password(passwordEncoder.encode(rawPassword))
                        .role(role)
                        .build()
        );
    }

    private void ensureDomaine(String libelle) {
        // Seed a domain only once, using a case-insensitive lookup.
        domaineRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> domaineRepository.save(Domaine.builder().libelle(libelle).build()));
    }

    private void ensureProfil(String libelle) {
        // Seed a profile only once, using a case-insensitive lookup.
        profilRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> profilRepository.save(Profil.builder().libelle(libelle).build()));
    }

    private void ensureStructure(String libelle) {
        // Seed a structure only once, using a case-insensitive lookup.
        structureRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> structureRepository.save(Structure.builder().libelle(libelle).build()));
    }

    private void ensureEmployeur(String nomEmployeur) {
        // Seed the default employer only once.
        employeurRepository.findByNomEmployeurIgnoreCase(nomEmployeur)
                .orElseGet(() -> employeurRepository.save(Employeur.builder().nomEmployeur(nomEmployeur).build()));
    }
}

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
        Role simpleUser = ensureRole(RoleName.SIMPLE_USER);
        Role responsable = ensureRole(RoleName.RESPONSABLE);
        Role adminRole = ensureRole(RoleName.ADMINISTRATEUR);

        if (userRepository.findByLogin("admin").isEmpty()) {
            userRepository.save(
                    AppUser.builder()
                            .login("admin")
                            .password(passwordEncoder.encode("admin123"))
                            .role(adminRole)
                            .build()
            );
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
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
    }

    private void ensureDomaine(String libelle) {
        domaineRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> domaineRepository.save(Domaine.builder().libelle(libelle).build()));
    }

    private void ensureProfil(String libelle) {
        profilRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> profilRepository.save(Profil.builder().libelle(libelle).build()));
    }

    private void ensureStructure(String libelle) {
        structureRepository.findByLibelleIgnoreCase(libelle)
                .orElseGet(() -> structureRepository.save(Structure.builder().libelle(libelle).build()));
    }

    private void ensureEmployeur(String nomEmployeur) {
        employeurRepository.findByNomEmployeurIgnoreCase(nomEmployeur)
                .orElseGet(() -> employeurRepository.save(Employeur.builder().nomEmployeur(nomEmployeur).build()));
    }
}

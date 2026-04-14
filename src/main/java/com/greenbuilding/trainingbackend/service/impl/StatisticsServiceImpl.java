package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.StatisticsResponse;
import com.greenbuilding.trainingbackend.entity.Formation;
import com.greenbuilding.trainingbackend.entity.Formateur;
import com.greenbuilding.trainingbackend.entity.Participant;
import com.greenbuilding.trainingbackend.repository.DomaineRepository;
import com.greenbuilding.trainingbackend.repository.EmployeurRepository;
import com.greenbuilding.trainingbackend.repository.FormationRepository;
import com.greenbuilding.trainingbackend.repository.FormateurRepository;
import com.greenbuilding.trainingbackend.repository.ParticipantRepository;
import com.greenbuilding.trainingbackend.repository.ProfilRepository;
import com.greenbuilding.trainingbackend.repository.RoleRepository;
import com.greenbuilding.trainingbackend.repository.StructureRepository;
import com.greenbuilding.trainingbackend.repository.UserRepository;
import com.greenbuilding.trainingbackend.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Builds summary metrics for the responsable dashboard.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DomaineRepository domaineRepository;
    private final ProfilRepository profilRepository;
    private final StructureRepository structureRepository;
    private final EmployeurRepository employeurRepository;
    private final FormateurRepository formateurRepository;
    private final ParticipantRepository participantRepository;
    private final FormationRepository formationRepository;

    @Override
    public StatisticsResponse getOverview() {
        List<Formation> formations = formationRepository.findAll();
        List<Participant> participants = participantRepository.findAll();
        List<Formateur> formateurs = formateurRepository.findAll();

        return new StatisticsResponse(
                userRepository.count(),
                roleRepository.count(),
                domaineRepository.count(),
                profilRepository.count(),
                structureRepository.count(),
                employeurRepository.count(),
                formateurRepository.count(),
                participantRepository.count(),
                formationRepository.count(),
                toCounts(formations.stream()
                        .collect(Collectors.groupingBy(
                                formation -> formation.getDomaine().getLibelle(),
                                Collectors.counting()
                        ))),
                toCounts(participants.stream()
                        .collect(Collectors.groupingBy(
                                participant -> participant.getStructure().getLibelle(),
                                Collectors.counting()
                        ))),
                toCounts(participants.stream()
                        .collect(Collectors.groupingBy(
                                participant -> participant.getProfil().getLibelle(),
                                Collectors.counting()
                        ))),
                toCounts(formateurs.stream()
                        .collect(Collectors.groupingBy(
                                formateur -> formateur.getType().name(),
                                Collectors.counting()
                        )))
        );
    }

    private List<StatisticsResponse.CategoryCount> toCounts(Map<String, Long> values) {
        return values.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByKey(Comparator.naturalOrder()))
                .map(entry -> new StatisticsResponse.CategoryCount(entry.getKey(), entry.getValue()))
                .toList();
    }
}

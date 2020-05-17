package ru.porodkin.everyonewillbethere.controller;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.VoyageRepository;
import ru.porodkin.everyonewillbethere.repository.specification.VoyageSpecification;

import java.util.List;

@Controller
@RequestMapping("/result")
public class ResultController {
    private final VoyageRepository voyageRepository;
    private final VoyageSpecification voyageSpecification;

    public ResultController(VoyageRepository voyageRepository, VoyageSpecification voyageSpecification) {
        this.voyageRepository = voyageRepository;
        this.voyageSpecification = voyageSpecification;
    }

    @GetMapping
    public String findVoyage(@RequestParam(name = "fromDest") String fromDest,
                             @RequestParam(name = "toDest") String toDest,
                             Model model){
        Specification<Voyage> spec = Specification.where(voyageSpecification.byFromDest(fromDest));
        spec = Specification.where(spec).and(voyageSpecification.byToDest(toDest));

        List<Voyage> voyages = voyageRepository.findAll(spec);

        System.out.println(voyages);

        model.addAttribute("from", fromDest);
        model.addAttribute("to", toDest);
        model.addAttribute("voyages", voyages);

        return "result_list";
    }
}

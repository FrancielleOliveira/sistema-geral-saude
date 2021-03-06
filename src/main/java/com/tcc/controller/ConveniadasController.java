package com.tcc.controller;

import com.tcc.model.Conveniada;
import com.tcc.repository.ConveniadasRepository;
import com.tcc.repository.filter.ConveniadaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by akemi on 27/02/17.
 */
@Controller
@RequestMapping("/saude")
public class ConveniadasController {


    @Autowired
    private ConveniadasRepository conveniadasRepository;

    @GetMapping("/conveniada")
    public ModelAndView novo(Conveniada conveniada){

        ModelAndView mv = new ModelAndView("cadastros/cadastro-conveniada");
        mv.addObject(conveniada);
        return mv;

    }

    @PostMapping("/conveniada")
    public ModelAndView salvar(@Valid Conveniada conveniada, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return novo(conveniada);
        }

        conveniadasRepository.save(conveniada);
        attributes.addFlashAttribute("mensagem", "Conveniada salvo com sucesso!");
        return new ModelAndView("redirect:/saude/conveniada");
    }

    @GetMapping("/conveniadas")
    public ModelAndView pesquisar(ConveniadaFilter conveniadaFilter ){

        ModelAndView mv = new ModelAndView("pesquisas/pesquisa-conveniada");
        mv.addObject("conveniadasRepository", conveniadasRepository.findByNomeContainingIgnoreCase(
                Optional.ofNullable(conveniadaFilter.getNome()).orElse("%")));

        return mv;
    }

}

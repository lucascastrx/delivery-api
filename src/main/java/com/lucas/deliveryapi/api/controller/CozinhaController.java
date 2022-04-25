package com.lucas.deliveryapi.api.controller;


import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.cozinha.input.CozinhaInputDTO;
import com.lucas.deliveryapi.api.model.cozinha.output.CozinhaDTO;
import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.repository.CozinhaRepository;
import com.lucas.deliveryapi.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private CozinhaRepository cozinhaRepository;

    private CozinhaService cozinhaService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<CozinhaDTO> list(){
        return mapperDTO.toCollection(cozinhaRepository.findAll(), CozinhaDTO.class);
    }



    @GetMapping("/{cozinhaId}")
    public CozinhaDTO findById(@PathVariable Long cozinhaId){
        return mapperDTO.transform(cozinhaService.findById(cozinhaId), CozinhaDTO.class);

//        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
//        if (cozinha.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(cozinha.get());
//        }
//
//        return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok(cozinha);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .headers(headers)
//                .build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO addCozinha(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO){
        var cozinha = mapperDTO.transform(cozinhaInputDTO, Cozinha.class);
        cozinha = cozinhaService.addCozinha(cozinha);
        return mapperDTO.transform(cozinha, CozinhaDTO.class);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO update(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO){

        var cozinha = cozinhaService.findById(cozinhaId);
        mapperDTO.copyToDomain(cozinhaInputDTO, cozinha);

         cozinha = cozinhaService.addCozinha(cozinha);
         return mapperDTO.transform(cozinha, CozinhaDTO.class);

//
//        Optional<Cozinha> o = cozinhaRepository.findById(cozinhaId);
//        if(o.isPresent()){
////        o.setNome(cozinha.getNome());
//            BeanUtils.copyProperties(cozinha,o.get(), "id");
//            return ResponseEntity.ok(cozinhaService.addCozinha(o.get()));
//        }
//        return ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/{cozinhaId}")
//    public ResponseEntity<Cozinha> delete(@PathVariable Long cozinhaId){
//        try {
//            cozinhaService.delete(cozinhaId);
//            return ResponseEntity.noContent().build();
//        } catch (EntityInviableException e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        } catch (EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{cozinhaId}")
     public void delete(@PathVariable Long cozinhaId){
//        try {
//            cozinhaService.delete(cozinhaId);
//        } catch (EntityNotFoundException e) {
//            //Classe ResponseStatusException eh uma alternativa ao uso do @ResponseStatus para lancamento de exceptions
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidade não pode ser encontrada");
//        }

            cozinhaService.delete(cozinhaId);
    }
}

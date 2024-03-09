package com.test.primary_project.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.primary_project.services.ProdutoService;
import com.test.primary_project.shared.ProdutoDTO;
import com.test.primary_project.view.models.ProdutoRequest;
import com.test.primary_project.view.models.ProdutoResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<List<ProdutoResponse>> getAll() {
        List<ProdutoDTO> produtos = produtoService.findAll();

        ModelMapper mapper = new ModelMapper();
        List<ProdutoResponse> response = produtos.stream()
                .map(dto -> mapper.map(dto, ProdutoResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> getById(@PathVariable Integer id) {
        try {
            Optional<ProdutoDTO> dto = produtoService.findById(id);

            ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

            return ResponseEntity.ok(Optional.of(produto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq) {
        ModelMapper mapper = new ModelMapper();

        ProdutoDTO dto = mapper.map(produtoReq, ProdutoDTO.class);

        dto = produtoService.saveProduto(dto);

        return ResponseEntity.ok(mapper.map(dto, ProdutoResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        produtoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable Integer id, @RequestBody ProdutoRequest produtoRed) {
        ModelMapper mapper = new ModelMapper();

        ProdutoDTO dto = mapper.map(produtoRed, ProdutoDTO.class);

        dto = produtoService.updateProduto(id, dto);

        return ResponseEntity.ok(mapper.map(dto, ProdutoResponse.class));
    }
}

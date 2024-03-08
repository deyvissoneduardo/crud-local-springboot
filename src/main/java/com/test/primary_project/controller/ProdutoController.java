package com.test.primary_project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.primary_project.model.Produto;
import com.test.primary_project.services.ProdutoService;

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
    public List<Produto> getAll(){
        return produtoService.obterTodos();
    }

    @PostMapping()
    public Produto adicionar(@RequestBody Produto produto){
        return produtoService.adcionar(produto);
    }

    @GetMapping("/{id}")
    public Optional<Produto> getById(@PathVariable Integer id){
        return produtoService.obterPorId(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
         produtoService.deletar(id);
         return "Produto "+id+" deletado";
    }

    @PutMapping("/{id}")
    public Produto update(@PathVariable Integer id, @RequestBody Produto produto) {
        return produtoService.atualizar(id, produto);
        
    }
}

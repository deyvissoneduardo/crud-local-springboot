package com.test.primary_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.primary_project.model.Produto;
import com.test.primary_project.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> obterTodos() {
        return repository.obterTodos();
    }

    public Optional<Produto> obterPorId(Integer id) {
        return repository.obterPorId(id);
    }

    public Produto adcionar(Produto produto) {
        return repository.adcionar(produto);
    }

    public void deletar(Integer id) {
        repository.deletar(id);
    }

    public Produto atualizar(Integer id, Produto produto) {
        produto.setId(id);
        return repository.atualizar(produto);
    }
}

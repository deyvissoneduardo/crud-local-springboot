package com.test.primary_project.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.primary_project.model.Produto;
import com.test.primary_project.model.exception.ResourceNotFoundException;
import com.test.primary_project.repository.ProdutoRepository;
import com.test.primary_project.shared.ProdutoDTO;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = repository.findAll();
        
        return produtos
            .stream()
            .map(produto -> new ModelMapper()
            .map(produto, ProdutoDTO.class))
            .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> findById(Integer id) {
        Optional<Produto> produto = repository.findById(id);
        
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Product Not Found");
        }
        
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
        
        return Optional.of(dto);
    }

    public ProdutoDTO saveProduto(ProdutoDTO produtoDto) {
        produtoDto.setId(null);
        
        ModelMapper mapper = new ModelMapper();
        
        Produto produto = mapper.map(produtoDto, Produto.class);
        
        produto = repository.save(produto);
        
        produtoDto.setId(produto.getId());
        
        return produtoDto;
    }

    public void deleteById(Integer id) {
        Optional<Produto> produto = repository.findById(id);
        
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Product Not Found");
        }
        
        repository.deleteById(id);
    }

    public ProdutoDTO updateProduto(Integer id, ProdutoDTO produtoDTO) {
        produtoDTO.setId(id);
        
        ModelMapper mapper = new ModelMapper();
        
        Produto produto = mapper.map(produtoDTO, Produto.class);
        
        repository.save(produto);
        
        return produtoDTO;
    }
}

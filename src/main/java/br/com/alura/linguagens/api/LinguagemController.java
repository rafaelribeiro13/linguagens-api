package br.com.alura.linguagens.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinguagemController {
    
    @Autowired
    LinguagemRepository repositorio;

    @PostMapping("/linguagens/cadastrar")
    public ResponseEntity<Linguagem> adicionarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return new ResponseEntity<Linguagem>(linguagemSalva, HttpStatus.CREATED);
    }
    
    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens() {
        return repositorio.findAll();
    }
    
    @GetMapping("/linguagens/ranking")
    public List<Linguagem> obterLinguagensOrdenadas() {
        List<Linguagem> linguagens = repositorio.findAll();
        Collections.sort(linguagens, (x, y) -> Integer.compare(x.getRanking(), y.getRanking()));
        return linguagens;    
    }

    @GetMapping("/linguagens/{id}")
    public Linguagem obterLinguagemPorId(@PathVariable String id) {
        Optional<Linguagem> optional = repositorio.findById(id);
        return optional.get();
    }

    @PutMapping("/linguagens/atualizar/{id}")
    public ResponseEntity<Linguagem> atualizar(@PathVariable String id, @RequestBody Linguagem linguagem) {
        Linguagem linguagemAtualizada = repositorio.findById(id).get();

        linguagemAtualizada.setTitle(linguagem.getTitle());
        linguagemAtualizada.setImage(linguagem.getImage());
        linguagemAtualizada.setRanking(linguagem.getRanking());

        return new ResponseEntity<Linguagem>(repositorio.save(linguagemAtualizada), HttpStatus.OK);
    }    

    @DeleteMapping("/linguagens/deletar/{id}")
    public String deletar(@PathVariable String id) {
        repositorio.deleteById(id);
        return "Excluido com sucesso!";
    }

}

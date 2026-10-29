package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servico;

    @Autowired
    private SerieRepository repositorio;

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados conversor;

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return servico.obterTodasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable Long id){
        return servico.obterPorId(id);
    }

    @PostMapping("/{nomeSerie}")
    public void inserirSerie(@PathVariable String nomeSerie){
        // 1. Monta a URL e busca no OMDb (substituindo espaços por + para a URL não quebrar)
        String url = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        String json = consumoApi.obterDados(url);

        // 2. Converte o JSON para o seu Record
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        // 3. Converte para a Entidade JPA e salva no banco
        Serie serie = new Serie(dados);
        repositorio.save(serie);

        System.out.println("Série " + serie.getTitulo() + " salva com sucesso!");

    }
}

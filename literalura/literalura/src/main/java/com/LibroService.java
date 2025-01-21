package com.aluracursos.literatura.service;

import com.aluracursos.literatura.model.Libro;
import com.aluracursos.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final RestTemplate restTemplate;
    private final LibroRepository libroRepository;

    private static final String GUTENDEX_API_URL = "https://gutendex.com/books/";

    @Autowired
    public LibroService(RestTemplate restTemplate, LibroRepository libroRepository) {
        this.restTemplate = restTemplate;
        this.libroRepository = libroRepository;
    }

    // Método para buscar libro por título en la API de Gutendex
    public Libro buscarLibroPorTitulo(String titulo) {
        // Llamada a la API de Gutendex
        String url = GUTENDEX_API_URL + "?search=" + titulo;
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

        if (response != null && response.getResults().size() > 0) {
            // Tomamos el primer libro de los resultados
            Libro libro = response.getResults().get(0);

            // Verificar si el libro ya existe en la base de datos
            Optional<Libro> existingLibro = libroRepository.findByTituloContainingIgnoreCase(libro.getTitulo()).stream().findFirst();
            if (existingLibro.isEmpty()) {
                // Si no existe, lo guardamos en la base de datos
                libroRepository.save(libro);
                return libro;
            } else {
                // Si el libro ya está en la base de datos
                return existingLibro.get();
            }
        }
        return null;  // Si no se encuentra el libro
    }

    // Método para listar todos los libros registrados en la base de datos
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }
}

package com.aluracursos.literatura.controller;

import com.aluracursos.literatura.service.LibroService;
import com.aluracursos.literatura.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // Endpoint para buscar libro por título
    @GetMapping("/buscar")
    public Libro buscarLibro(@RequestParam String titulo) {
        return libroService.buscarLibroPorTitulo(titulo);
    }

    // Endpoint para listar todos los libros
    @GetMapping("/listar")
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }
}

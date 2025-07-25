package com.blanquita.blanquitagestion.service;

import com.blanquita.blanquitagestion.dao.ProductoRepository;
import com.blanquita.blanquitagestion.entity.Producto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id){
        return productoRepository.findById(id);
    }

    public Optional<Producto> actualizarProducto(Long productoId, Producto nuevo){
        return productoRepository.findById(productoId).map( anterior -> {
            anterior.setUnidadMedida(nuevo.getUnidadMedida());
            anterior.setEn_stock(nuevo.isEn_stock());
            anterior.setActivo(nuevo.isActivo());
            anterior.setNombre(nuevo.getNombre());
            anterior.setPrecio(nuevo.getPrecio());
            return productoRepository.save(anterior);
        });
    }

    @Transactional
    public void eliminarProducto(Long id) throws Exception { //baja logica, no elimina realmente de la bd
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        if(producto.isActivo()) {
            producto.setActivo(false);
            productoRepository.save(producto);
        } else {
            throw new Exception("El producto ya se encuentra inactivo");
        }
    }

    public Producto crearProducto(Producto nuevoProducto) throws Exception{
        //Si hay y tiene activo = false, en vez de crearlo lo reactiva y le pone los datos nuevos.(Es decir, los del producto 'nuevo')
        //Si hay y tiene activo = true no permite crearlo

        if(productoRepository.existsByNombre(nuevoProducto.getNombre()) ){
            Producto productoExistente = productoRepository.findByNombre(nuevoProducto.getNombre()).get();
            if(productoExistente.isActivo()){
                throw new Exception("Ya existe el producto " + nuevoProducto.getNombre());
            }
            else{
                Long idProducto = productoRepository.findByNombre(nuevoProducto.getNombre()).get().getId();
                return actualizarProducto(idProducto, nuevoProducto)
                        .orElseThrow(() -> new Exception("Error al reactivar el producto " + nuevoProducto.getNombre()));
            }
        }else{
            return productoRepository.save(nuevoProducto);
        }

    }

    public Producto actualizarStock(Long id){
        Producto producto = productoRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        producto.setEn_stock(!producto.isEn_stock());

        return productoRepository.save(producto);
    }

}

package com.blanquita.blanquitagestion.service;

import com.blanquita.blanquitagestion.dao.ProductoRepository;
import com.blanquita.blanquitagestion.dao.VentaRepository;
import com.blanquita.blanquitagestion.dto.DetalleVentaDTO;
import com.blanquita.blanquitagestion.dto.VentaDTO;
import com.blanquita.blanquitagestion.entity.DetalleVenta;
import com.blanquita.blanquitagestion.entity.Producto;
import com.blanquita.blanquitagestion.entity.Venta;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public VentaDTO crearVenta(Venta venta) {
        venta.setFechaHora(LocalDateTime.now());

        BigDecimal totalVenta = BigDecimal.ZERO;

        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + detalle.getProducto().getId()));

            if(producto.isActivo()){
                detalle.setProducto(producto);
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.setVenta(venta);

                BigDecimal subtotal = producto.getPrecio().multiply(detalle.getCantidad());
                detalle.setSubtotal(subtotal);

                totalVenta = totalVenta.add(subtotal);
            }else{
                throw new IllegalStateException("El producto '" + producto.getNombre() + "' no está activo.");
            }

        }
        venta.setTotal(totalVenta);
        Venta ventaGuardada = ventaRepository.save(venta);
        return convertirEntidadADTO(ventaGuardada);
    }

    public List<VentaDTO> getAllVentas(){
        return ventaRepository.findAll()
                .stream()
                .map(this::convertirEntidadADTO)
                .toList();
    }

    public Optional<VentaDTO> getVentaById(Long id) {
        return ventaRepository.findById(id)
                .map(this::convertirEntidadADTO);
    }

    @Transactional
    public void eliminarVenta(Long id) throws Exception { //baja logica, no elimina realmente de la bd
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new Exception("No encontrado"));

        if(venta.isActivo()) {
            venta.setActivo(false);
            ventaRepository.save(venta);
        } else {
            throw new Exception("Ya se encuentra inactivo");
        }
    }

    private VentaDTO convertirEntidadADTO(Venta venta) {
        List<DetalleVentaDTO> detallesDTO = venta.getDetalles().stream()
                .map(detalle -> new DetalleVentaDTO(
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()
                ))
                .toList();

        return new VentaDTO(
                venta.getId(),
                venta.getFechaHora(),
                venta.getFormaPago(),
                venta.getTotal(),
                detallesDTO
        );
    }

}

package com.blanquita.blanquitagestion.service;

import com.blanquita.blanquitagestion.dao.PedidoRepository;
import com.blanquita.blanquitagestion.dao.VentaRepository;
import com.blanquita.blanquitagestion.dto.DetalleVentaDTO;
import com.blanquita.blanquitagestion.dto.PedidoDTO;
import com.blanquita.blanquitagestion.dto.PedidoRequestDTO;
import com.blanquita.blanquitagestion.dto.VentaDTO;
import com.blanquita.blanquitagestion.entity.Pedido;
import com.blanquita.blanquitagestion.entity.Venta;
import com.blanquita.blanquitagestion.service.spec.PedidoSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PedidoService {



    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired VentaRepository ventaRepository;

    @Autowired
    private VentaService ventaService;

    public Optional<Pedido> getEntity(Long id){return pedidoRepository.findById(id);}

    public PedidoDTO crearPedido(PedidoRequestDTO pedidoRequest) {

        VentaDTO ventaCreadaDTO = ventaService.crearVenta(pedidoRequest.venta());


        Long nuevaVentaId = ventaCreadaDTO.id();
        Venta ventaGuardada = ventaRepository.findById(nuevaVentaId)
                .orElseThrow(() -> new EntityNotFoundException("Error: La venta recién creada no se encontró. ID: " + nuevaVentaId));

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setNombreCliente(pedidoRequest.nombreCliente());
        nuevoPedido.setDireccion(pedidoRequest.direccion());
        nuevoPedido.setVenta(ventaGuardada);
        nuevoPedido.setEstado(Pedido.Estado.PENDIENTE);
        nuevoPedido.setActivo(true);

        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        return convertirEntidadADTO(pedidoGuardado);
    }

    public Page<PedidoDTO> findPedidos(Long id, String nombreCliente, Pedido.Estado estado, LocalDate fecha, Pageable pageable) {
        Specification<Pedido> spec = PedidoSpecification.withFilters(id, nombreCliente, estado, fecha);
        Page<Pedido> pedidos = pedidoRepository.findAll(spec, pageable);
        return pedidos.map(this::convertirEntidadADTO);
    }

    public List<PedidoDTO> getAllPedidos(){
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertirEntidadADTO)
                .toList();
    }

    public Optional<PedidoDTO> getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .map(this::convertirEntidadADTO);
    }

    private PedidoDTO convertirEntidadADTO(Pedido pedido) {


        Venta ventaAsociada = pedido.getVenta();

        List<DetalleVentaDTO> detallesDTO = ventaAsociada.getDetalles().stream()
                .map(detalle -> new DetalleVentaDTO(
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()
                ))
                .toList();

        return new PedidoDTO(
                pedido.getId(),
                ventaAsociada.getId(),
                pedido.getDireccion(),
                pedido.getNombreCliente(),
                pedido.getEstado().getDescripcion(),
                pedido.isActivo(),
                ventaAsociada.getFechaHora(),
                ventaAsociada.getTotal(),
                detallesDTO
        );
    }

    @Transactional
    public void eliminarPedido(Long id) throws Exception { //baja logica, no elimina realmente de la bd
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new Exception("No encontrado"));

        if(pedido.isActivo()) {
            pedido.setActivo(false);
            pedidoRepository.save(pedido);
        } else {
            throw new Exception("Ya se encuentra inactivo");
        }
    }

    public PedidoDTO actualizarEstado(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el pedido con el ID: " + id));
        pedido.setEstado(pedido.getEstado().siguiente());
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return convertirEntidadADTO(pedidoGuardado);
    }


}


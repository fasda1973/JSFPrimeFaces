package br.com.fasda.erp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.fasda.erp.repository.EmpresaRepository;
import br.com.fasda.erp.repository.UsuarioRepository;

@Named
@ViewScoped
public class DashboardBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioRepository usuarioRepository;    
    @Inject
    private EmpresaRepository empresaRepository;

    private Long totalUsuarios;
    private Long totalEmpresas;
    private BarChartModel barModel;

    public void inicializar() {
        this.totalUsuarios = usuarioRepository.contarTodos();
        this.totalEmpresas = empresaRepository.contarTodas();
        createBarModel();
    }
    
    private void createBarModel() {
    	barModel = new BarChartModel();

        // Série 1: Usuários (Ficará Azul)
        ChartSeries serieUsuarios = new ChartSeries();
        serieUsuarios.setLabel("Usuários");
        serieUsuarios.set("Cadastros", totalUsuarios); // O nome do eixo X deve ser igual

        // Série 2: EmpresaRepository (Ficará Verde)
        ChartSeries serieEmpresas = new ChartSeries();
        serieEmpresas.setLabel("EmpresaRepository");
        serieEmpresas.set("Cadastros", totalEmpresas);

        barModel.addSeries(serieUsuarios);
        barModel.addSeries(serieEmpresas);

        // Configurações visuais
        barModel.setTitle("Comparativo de Cadastros");
        barModel.setAnimate(true);
        barModel.setLegendPosition("ne");
        
        // Agora sim! A primeira série usa a primeira cor, a segunda usa a segunda.
        barModel.setSeriesColors("2196F3, 4CAF50"); 

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        // Ajuste o Max se quiser dar um "respiro" no topo do gráfico
        yAxis.setMax(Math.max(totalUsuarios, totalEmpresas) + 5);        
    }

    // Getters para totalUsuarios, totalEmpresas e barModel
    public Long getTotalUsuarios() {
		return totalUsuarios;
	}

	public Long getTotalEmpresas() {
		return totalEmpresas;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}
   
    
}
package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaPrincipal{

    private Stage palco;
    private MenuBar menu;
    private Menu desenho;
    private Menu opcoes;
    private MenuItem menuLimpar;
    private MenuItem menuPontos;
    private MenuItem menuRetas;
    private MenuItem menuCirculos;
    private MenuItem menuCurvaDragao;
    private MenuItem menuRetasCirculos;
    private Canvas canvas;
    private ControladorDeEventos controladorDeEventos;
	
	public TelaPrincipal(Stage palco) {
		this.palco = palco;
		desenharTela();
	}

	public void desenharTela(){
			
		palco.setWidth(750);
		palco.setHeight(750);

		//criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
		controladorDeEventos = new ControladorDeEventos(canvas);

		// Painel para os componentes
        BorderPane pane = new BorderPane();
        
        //Criando Menu
        menu = new MenuBar();
        desenho = new Menu("Desenho");
        opcoes = new Menu("Opções");
        menuPontos = new MenuItem("Pontos");
        menuRetas = new MenuItem("Retas");
        menuCirculos = new MenuItem("Círculos");
        menuLimpar = new MenuItem("Limpar");
    	menuCurvaDragao = new MenuItem("Curva do Dragão");
		menuRetasCirculos = new MenuItem("Retas e Círculos");
        desenho.getItems().addAll(menuPontos,menuRetas,menuCirculos, menuCurvaDragao, menuRetasCirculos);
    	opcoes.getItems().add(menuLimpar);
    	menu.getMenus().addAll(desenho,opcoes);
    	
    	//Criando footer
    	GridPane grid = montarMenuOpcoesGraficas();
    	VBox menus = new VBox();
    	menus.getChildren().addAll(menu,grid);
    	        
    	// atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        pane.setTop(menus);
        
        
    	atribuirEventosAosComponentesGraficos();
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
		
	}
	
	private void atribuirEventosAosComponentesGraficos(){
		//menu
		this.menuRetas.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.RETA);
		});
		
		this.menuPontos.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.PONTO);
		});

		this.menuCirculos.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.CIRCULO);
		});

		this.menuLimpar.setOnAction(e->{
			controladorDeEventos.limparCanvas();
		});

		this.menuCurvaDragao.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.CURVA_DO_DRAGAO);
		});

		this.menuRetasCirculos.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.RETAS_CIRCULOS);
		});

		//canvas
        canvas.setOnMouseMoved(event -> {
            palco.setTitle("(Posição do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
        });

        canvas.setOnMousePressed(event -> {
        	controladorDeEventos.onCanvasMousePressed(event);
        });	
	}
	
	private GridPane montarMenuOpcoesGraficas() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(5);
		
		// Color Picker 
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(e->{
			controladorDeEventos.setCor(colorPicker.getValue());
		});
		
		Spinner<Integer> diametroLinhas = new Spinner<Integer>();
		SpinnerValueFactory<Integer> diametros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,3);
		diametroLinhas.setValueFactory(diametros);
		diametroLinhas.valueProperty().addListener(e->{
			controladorDeEventos.setDiametro(diametros.getValue());
		});
		
		grid.add(new Label("Cor: "), 0, 0);
		grid.add(colorPicker, 1, 0);
		grid.add(new Label("Diâmetro dos Pontos: "),2, 0);
		grid.add(diametroLinhas, 3, 0);
				
		return grid;
	}
}

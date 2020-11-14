package gui;

import controladores.ControladorDeEventos;
import controladores.TipoDesenho;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import primitivos.*;
import utils.AlertaPersonalizado;
import utils.Figura;
import utils.XMLParser;

import java.io.File;

public class TelaPrincipal {

    private final ViewPort viewPort;
    private final Stage palco;
    private MenuItem menuLimpar;
    private MenuItem menuCurvaDragao;
    private MenuItem menuRetaElastica;
    private MenuItem menuCirculoElastico;
    private MenuItem menuRetanguloElastico;
    private MenuItem menuPoligonoElastico;
    private MenuItem menuRetaPoligonalElastica;
    private MenuItem menuApagarPrimitivos;
    private MenuItem menuSelecionarPrimitivos;
    private MenuItem menuDesfazerSelecaoPrimitivos;
    private MenuItem menuAbrirArquivo;
    private MenuItem menuSalvarArquivo;

    private Canvas canvas;
    private ControladorDeEventos controladorDeEventos;
    private FileChooser fileChooser;

    public static int LARGURA_CANVAS = 1000;
    public static int ALTURA_CANVAS = 700;
    public static double PROPORCAO = 0.5;

    public TelaPrincipal(Stage palco) {
        this.palco = palco;
        this.viewPort = new ViewPort(new Stage(), PROPORCAO);

        desenharTela();
    }

    public void desenharTela() {

        palco.setWidth(LARGURA_CANVAS);
        palco.setHeight(ALTURA_CANVAS);
        palco.setResizable(false);

        //criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
        controladorDeEventos = new ControladorDeEventos(canvas);

        // Painel para os componentes
        BorderPane pane = new BorderPane();

        //Criando Menu
        MenuBar menu = new MenuBar();

        Menu desenho = new Menu("Desenho");
        Menu opcoes = new Menu("Opcoes");
        Menu arquivo = new Menu("Arquivo");

        menuLimpar = new MenuItem("Limpar");
        menuCurvaDragao = new MenuItem("Curva do DragÃ£o");
        menuRetaElastica = new MenuItem("Retas");
        menuCirculoElastico = new MenuItem("Circulos");
        menuRetanguloElastico = new MenuItem("Retangulos");
        menuPoligonoElastico = new MenuItem("Poligonos");
        menuRetaPoligonalElastica = new MenuItem("Reta Poligonal");
        menuApagarPrimitivos = new MenuItem("Apagar Desenhos Selecionados");
        menuSelecionarPrimitivos = new MenuItem("Selecionar Formas Desenhadas");
        menuDesfazerSelecaoPrimitivos = new MenuItem("Desfazer Selecao de Formas");
        menuAbrirArquivo = new MenuItem("Abrir XML");
        menuSalvarArquivo = new MenuItem("Salvar em XML");

        desenho.getItems().addAll(menuRetaElastica, menuCirculoElastico, menuRetanguloElastico, menuPoligonoElastico, menuRetaPoligonalElastica, menuCurvaDragao);
        opcoes.getItems().addAll(menuSelecionarPrimitivos, menuDesfazerSelecaoPrimitivos, menuApagarPrimitivos, menuLimpar);
        arquivo.getItems().addAll(menuAbrirArquivo, menuSalvarArquivo);
        menu.getMenus().addAll(desenho, arquivo, opcoes);


        //Criando footer
        GridPane grid = montarMenuOpcoesGraficas();
        VBox menus = new VBox();
        menus.getChildren().addAll(menu, grid);
        menus.setMinHeight(60);
        menus.setMaxHeight(60);

        // atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        pane.setTop(menus);
        atribuirEventosAosComponentesGraficos();
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();

    }

    // Vincula o dos componentes do MENU aos eventos declarados no ControladorDeEventos de componentes graficos.
    private void atribuirEventosAosComponentesGraficos() {
        this.menuLimpar.setOnAction(e -> AlertaPersonalizado.criarAlertaComCallback("A execucao dessa operacao resulta na perda de todos os dados desenhados.\n "
                + "Deseja continuar?", () -> {
            controladorDeEventos.limparCanvas();
            viewPort.getControladorDeEventos().limparCanvas();
        }));

        this.menuCurvaDragao.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.CURVA_DO_DRAGAO);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.CURVA_DO_DRAGAO);
        });
        this.menuRetaElastica.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.RETA_ELASTICA);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.RETA_ELASTICA);
        });
        this.menuCirculoElastico.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.CIRCULO_ELASTICO);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.CIRCULO_ELASTICO);
        });
        this.menuRetanguloElastico.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.RETANGULO_ELASTICO);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.RETANGULO_ELASTICO);
        });
        this.menuPoligonoElastico.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.POLIGONO_ELASTICO);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.POLIGONO_ELASTICO);
        });
        this.menuRetaPoligonalElastica.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.RETA_POLIGONAL);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.RETA_POLIGONAL);
        });
        this.menuSelecionarPrimitivos.setOnAction(e -> {
            controladorDeEventos.setTipoDesenho(TipoDesenho.SELECIONA_DESENHO);
            viewPort.getControladorDeEventos().setTipoDesenho(TipoDesenho.SELECIONA_DESENHO);
        });

        this.menuDesfazerSelecaoPrimitivos.setOnAction(e -> {
            controladorDeEventos.setTransformacaoEmAndamento(false);
            viewPort.getControladorDeEventos().setTransformacaoEmAndamento(false);
            controladorDeEventos.desfazerSelecao();
            viewPort.getControladorDeEventos().desfazerSelecao();
        });


        this.menuAbrirArquivo.setOnAction(ev ->
                AlertaPersonalizado.criarAlertaComCallback("A execucao dessa operacao resulta na perda de todos os desenhos nao salvos.\n "
                + "Deseja continuar?", () -> {
                    controladorDeEventos.setTransformacaoEmAndamento(false);
                    viewPort.getControladorDeEventos().setTransformacaoEmAndamento(false);
                    abriXML();
                }));

// é o que escolhe um arquivo no sistema , pra poder salvar, entao com o xml parser ele transforna o array de formas no xml e salva no arquivo
        this.menuSalvarArquivo.setOnAction(ev -> {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
            File file = fileChooser.showSaveDialog(this.palco);
            if (file != null) {
                try {
                    XMLParser<Figura> parser = new XMLParser<>(file);
                    Figura figura = new Figura();
                    figura.setObjetosDesenhados(this.controladorDeEventos.getDesenhador().getObjetosDesenhados());
                    parser.saveFile(figura, new Class[]{
                            Figura.class,
                            Retangulo.class,
                            Ponto.class,
                            Reta.class,
                            Circulo.class,
                            Poligono.class,
                            LinhaPoligonal.class,
                            PontoGr.class
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // canvas
        canvas.setOnMouseMoved(event -> palco.setTitle("(Posicao do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")"));

        canvas.setOnMousePressed(event -> {
            controladorDeEventos.onCanvasMousePressed(event);
            viewPort.getControladorDeEventos().onCanvasMousePressed(event);
        });

        canvas.setOnMouseDragged(ev -> {
            controladorDeEventos.onMouseDraggedPrimitivosElasticos(ev);
            viewPort.getControladorDeEventos().onMouseDraggedPrimitivosElasticos(ev);
        });
        canvas.setOnMouseReleased(ev -> {
            controladorDeEventos.onMouseReleasedPrimitivosElasticos(ev);
            viewPort.getControladorDeEventos().onMouseReleasedPrimitivosElasticos(ev);
        });
    }

    // Menu de cores e diametro das linhas

    private GridPane montarMenuOpcoesGraficas() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);

        // Color Picker
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> controladorDeEventos.getDesenhador().setCor(colorPicker.getValue()));

        Spinner<Integer> diametroLinhas = new Spinner<>();
        SpinnerValueFactory<Integer> diametros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);

        diametroLinhas.setValueFactory(diametros);
        diametroLinhas.setMaxWidth(80);
        diametroLinhas.valueProperty().addListener(e -> controladorDeEventos.getDesenhador().setDiametro(diametros.getValue()));

        grid.add(new Label("Cor: "), 0, 0);
        grid.add(colorPicker, 1, 0);
        grid.add(new Label("Diametro: "), 2, 0);
        grid.add(diametroLinhas, 3, 0);

        return grid;
    }

    private void abriXML() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File file = fileChooser.showOpenDialog(this.palco);
        if (file != null) {
            try {
                XMLParser<Figura> parser = new XMLParser<>(file);
                Figura figura = parser.toObject(new Class[]{
                        Figura.class,
                        Retangulo.class,
                        Ponto.class,
                        Reta.class,
                        Ponto.class,
                        Circulo.class,
                        Poligono.class,
                        LinhaPoligonal.class,
                        PontoGr.class
                });
                //desenhando objetos obtidos
                this.controladorDeEventos.getDesenhador().setObjetosDesenhados(
                        figura.getObjetosDesenhados()
                );
                this.controladorDeEventos.getDesenhador().desenharObjetosArmazenados(null);

                this.viewPort.getControladorDeEventos().getDesenhador().setObjetosDesenhados(
                        figura.getObjetosDesenhados()
                );
                this.viewPort.getControladorDeEventos().getDesenhador().desenharObjetosArmazenados(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

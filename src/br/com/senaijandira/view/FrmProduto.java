package br.com.senaijandira.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import br.com.senaijandira.dao.ProdutoDAO;
import br.com.senaijandira.model.Produto;

public class FrmProduto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painel_conteudo;
	private JPanel painel_tabela;
	private JTable tabela;
	private JScrollPane scrollTabela;
	private DefaultTableModel modeloTabela;
	
	private ProdutoDAO dao;

	public FrmProduto() {
		setBounds(100, 100, 750, 567);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		painel_conteudo = new JPanel();
		painel_conteudo.setBorder(new EmptyBorder(5, 5, 5, 5));
		painel_conteudo.setLayout(new BorderLayout(0, 0));
		setContentPane(painel_conteudo);

		JPanel painel_principal = new JPanel();
		painel_conteudo.add(painel_principal, BorderLayout.CENTER);
		painel_principal.setLayout(null);

		JPanel painel_menu = new JPanel();
		painel_menu.setBounds(0, 11, 200, 500);
		painel_menu.setBorder(null);
		painel_principal.add(painel_menu);
		painel_menu.setLayout(null);

		JPanel painel_img = new JPanel();
		painel_img.setBackground(Color.DARK_GRAY);
		painel_img.setBounds(39, 11, 120, 120);
		painel_menu.add(painel_img);

		JButton btnCadastros = new JButton("Cadastros");
		btnCadastros.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnCadastros.setBounds(10, 159, 180, 50);
		painel_menu.add(btnCadastros);

		JButton btnGerenciamento = new JButton("Gerenciamento");
		btnGerenciamento.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnGerenciamento.setBounds(10, 220, 180, 50);
		painel_menu.add(btnGerenciamento);

		JButton btnControleDeEstoque = new JButton("Controle de Estoque");
		btnControleDeEstoque.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnControleDeEstoque.setBounds(10, 281, 180, 50);
		painel_menu.add(btnControleDeEstoque);

		JButton btnRelatorio = new JButton("Relat\u00F3rios");
		btnRelatorio.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnRelatorio.setBounds(10, 342, 180, 50);
		painel_menu.add(btnRelatorio);

		JButton btnExpedicao = new JButton("Expedi\u00E7\u00E3o");
		btnExpedicao.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnExpedicao.setBounds(10, 403, 180, 50);
		painel_menu.add(btnExpedicao);

		JLabel lbl_titulo = new JLabel("Gerenciamento de Produtos");
		lbl_titulo.setBounds(231, 11, 461, 82);
		lbl_titulo.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
		painel_principal.add(lbl_titulo);

		painel_tabela = new JPanel();
		painel_tabela.setBounds(210, 132, 514, 337);
		painel_tabela.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Produtos", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		painel_principal.add(painel_tabela);
		painel_tabela.setLayout(null);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receberDados();
			}
		});
		btnVisualizar.setFont(new Font("Arial Black", Font.BOLD, 14));
		btnVisualizar.setBounds(210, 478, 180, 50);
		painel_principal.add(btnVisualizar);
		CriarTabela();

		setVisible(true);
	}

	// M�todo para criar uma tabela
	public void CriarTabela() 
	{
		scrollTabela = new JScrollPane();
		scrollTabela.setBounds(10, 21, 494, 305);
		painel_tabela.add(scrollTabela);

		tabela = new JTable();

		modeloTabela = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			
			@Override
			public boolean isCellEditable(int linha, int coluna) {
				return false;
			}
		};

		String[] nomeColunas = {"Id", "Nome", "Descri��o", "Localiza��o"};

		modeloTabela.setColumnIdentifiers(nomeColunas);

		GerarProduto();

		tabela.setModel(modeloTabela);
		scrollTabela.setViewportView(tabela);

		// Deixar as colunas da tabela fixas
		tabela.getTableHeader().setReorderingAllowed(false);

		
		((DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setResizable(false);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabela.getColumnModel().getColumn(1).setResizable(false);
		tabela.getColumnModel().getColumn(2).setResizable(false);
		tabela.getColumnModel().getColumn(3).setResizable(false);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(20);


		tabela.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollTabela.setViewportView(tabela);
		scrollTabela.getViewport().setBackground(new Color(255, 255, 255));

	}

	// Apaga toda a tabela e gera os clientes novamente
	public void atualizarTabela(){
		modeloTabela.setRowCount(0);
		GerarProduto();
	}


	public void GerarProduto() 
	{
		ProdutoDAO produtoDAO = new ProdutoDAO();
		ArrayList<Produto> produtos = new ArrayList<Produto>();

		produtos = produtoDAO.selectAll();

		Object[] linha = new Object[4];

		for (Produto produto : produtos) 
		{
			linha[0] = produto.getIdProduto();
			linha[1] = produto.getNome();
			linha[2] = produto.getDescricao();
			linha[3] = produto.getLocalizacao();

			modeloTabela.addRow(linha);
		}
	}
	
	public void receberDados() {
		//FrmCliente frmCliente = new FrmCliente(op, op + "Cliente");
		
		//********Receber dados pela linha selecionada
		FrmProdutoUnico frmProdutoUnico = new FrmProdutoUnico("Produto");
		try {
			int linha = tabela.getSelectedRow();
		
			int id = (int) tabela.getValueAt(linha, 0); 
			
			dao = new ProdutoDAO();
			
			Produto produto = new Produto();
			
			produto = dao.selectById(id);
			
			
			frmProdutoUnico.setTxtID(produto.getIdProduto());
			frmProdutoUnico.setTxtNome(produto.getNome());
			frmProdutoUnico.setTxtTipo(produto.getTipoProduto());
			frmProdutoUnico.setTxtValorUnitario(produto.getValorUnitario());
			frmProdutoUnico.setTxtQtdeFardo(produto.getQtdeFardo());
			frmProdutoUnico.setTxtQtdeEstoque(produto.getQtdeEstoque());
			frmProdutoUnico.setTxtPeso(produto.getPeso());
			frmProdutoUnico.setTxtVolume(produto.getVolume());
			frmProdutoUnico.setTxtLocalizacao(produto.getLocalizacao());
			frmProdutoUnico.setTxtIpi(produto.getIpi());
			frmProdutoUnico.setTxtDemandaMensal(produto.getDemandaMensal());
			frmProdutoUnico.setTxtTempoRessupri(produto.getTempoRessuprimento());
			frmProdutoUnico.setTxtPontoRessupri(produto.getPontoRessuprimento());
			frmProdutoUnico.setTxtLoteCompra(produto.getLoteCompras());
			frmProdutoUnico.setTxtEstoqueMax(produto.getEstoqueMaximo());
			frmProdutoUnico.setTxtDesc(produto.getDescricao());
			
			frmProdutoUnico.setVisible(true);
			
			} catch(Exception erro) {
			System.out.println(erro.getMessage());
			erro.printStackTrace();
			JOptionPane.showMessageDialog(null, "Por favor, "
					+ "selecione um contato", "Error Message", JOptionPane.ERROR_MESSAGE);
			
			
		}
	}
}
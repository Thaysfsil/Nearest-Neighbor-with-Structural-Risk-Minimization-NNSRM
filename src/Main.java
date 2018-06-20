import java.awt.Color;
import java.awt.Desktop;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.jfree.chart.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

	public static void main(String[] args) {
		// localizacao arquivos

		Arquivo file = new Arquivo("conjuntotreinamento.txt");
		Arquivo fileTest = new Arquivo("conjuntoteste.txt");
		double x, y, distance;
		int type;
		int k = 0;
		double rEmp = 100.0;
		VectorObject solution = null;
		VectorObject objTest;
		ArrayList<VectorObject> vector = new ArrayList<VectorObject>();
		ArrayList<VectorObject> vectorTest = new ArrayList<VectorObject>();
		VectorPartner partner;
		ArrayList<VectorPartner> partnerList = new ArrayList<VectorPartner>();
		ArrayList<VectorPartner> partnerListTest = new ArrayList<VectorPartner>();
		ArrayList<VectorObject> result = new ArrayList<VectorObject>();
		ArrayList<VectorPartner> sortList = new ArrayList<VectorPartner>();

		// ler o arquivo e armazena em um objeto denominado "vector" seu x,y e
		// classe (type)
		while (!file.isEndOfFile()) {
			do {
				x = file.readDouble();
				y = file.readDouble();
				type = file.readInt();
				vector.add(new VectorObject(x, y, type));
			} while (!file.isEndOfLine());
		}
		file.close();

		// faz o mesmo para o conjunto teste
		while (!fileTest.isEndOfFile()) {
			do {
				x = fileTest.readDouble();
				y = fileTest.readDouble();
				type = fileTest.readInt();
				vectorTest.add(new VectorObject(x, y, type));
			} while (!fileTest.isEndOfLine());
		}
		fileTest.close();

		// Cria uma lista de pares (partner) armazenado os vertores (objeto)
		// parceiros
		// e suas respectivas distancias.
		for (int i = 0; i < vector.size(); i++) {
			for (int l = 0; l < vector.size() - 1; l++) {
				if (i != l) {
					VectorObject a, b;
					a = vector.get(i);
					b = vector.get(l);

					// cria uma lista de menor distancia entre diferentes tipos
					if (a.type != b.type) {
						partner = new VectorPartner(a, b, distance(a, b));
						partnerList.add(partner);
						//partner.getResult();
					}
				}
			}
		}

		// faz o mesmo para o conjunto teste mas em comparacao com o conjunto
		// treianamento
		for (int i = 0; i < vectorTest.size(); i++) {
			for (int l = 0; l < vector.size() - 1; l++) {
				if (i != l) {
					VectorObject a, b;
					a = vectorTest.get(i);
					b = vector.get(l);
					partner = new VectorPartner(a, b, distance(a, b));
					// cria uma lista de menor distancia entre diferentes tipos
					if (a.type != b.type) {
						partner = new VectorPartner(a, b, distance(a, b));
						partnerListTest.add(partner);
					}
				}
			}
		}

		// Cria uma lista, organizada por distancias;
		sortList = partnerList;
		Collections.sort(sortList);
		
		
		 /// Armazena a fronteira no Array result

		result.add(sortList.get(0).vector1);
		ArrayList<Double> distanceResult = new ArrayList<Double>();
		double keyDistance;
		int key = 0;


		while (rEmp > 0) {

			solution = sortList.get(k).vector1;
			distance = sortList.get(k).distance;
			keyDistance = 100.00;
			distanceResult.add(distance);

			VectorObject vectorT1 = sortList.get(k).vector1;
			VectorObject vectorT2 = sortList.get(k).vector2;

			for (int z = 0; z < result.size(); z++) {
				objTest = result.get(z);

				// Verifica a distancia entre o vetor em result com o vetor a ser testado.
				for (int i = 0; i < sortList.size(); i++) {

					VectorObject vector1 = sortList.get(i).vector1;
					VectorObject vector2 = sortList.get(i).vector2;
					double partnerDistance = sortList.get(i).distance;

					if ((objTest.equals(vector1) && vectorT2.equals(vector2))
							|| (vectorT1.equals(vector1) && objTest.equals(vector2))) {
						if (partnerDistance < keyDistance) {
							keyDistance = partnerDistance;
							solution = vectorT2;
							key++;
						}

					}
					/* verifca se objTest eh o objeto de menor distancia para o VectorT,
					 *  adiciona enquanto durar o array.*/
				}
			}
			// se o objeto de menor distancia nao for de mesma classe armazena novo objeto
			if (solution.type != vectorT1.type) {
					result.add(solution);
					distanceResult.add(keyDistance);		 
			}
			// taxa de erro empirico
			rEmp = 100 - ((100 * key) / vector.size());
			k++;

			
		}
		// verificando acerto do codigo

		XYSeries series1 = new XYSeries("Treinamento");
		XYSeries series2 = new XYSeries("Dispersao");
		for (int t = 0; t < vector.size(); t++) {

			if (!result.contains(vector.get(t))) {
				series1.add(vector.get(t).x, vector.get(t).y);
			}

		}

		for (int i = 0; i < result.size(); i++) {

			series2.add(result.get(i).x, result.get(i).y);

		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);

		JFreeChart graph = ChartFactory.createScatterPlot("NNSRM", "x", "y",
				dataset);
		graph.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		XYItemRenderer renderer = graph.getXYPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.gray);
        
        
        

		ChartPanel panel = new ChartPanel(graph, true);
		panel.setPreferredSize(new java.awt.Dimension(1000, 500));
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);
		
		try {
			File fl = new File("chart2.jpg");
			ChartUtilities.saveChartAsJPEG(fl , graph, 500, 300);
			Desktop dt = Desktop.getDesktop();
		    dt.open(fl);
		} catch (IOException e) {
			System.err.println("Problemas ocorreram na criacao do grafico.");
		}

			}

	public static double distance(VectorObject a, VectorObject b) {
		return (double) Math.sqrt(Math.pow((a.x - b.x), 2)
				+ Math.pow((a.y - b.y), 2));
	}

}

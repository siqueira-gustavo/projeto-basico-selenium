package com.example.core;

import static com.example.core.DriverFactory.getDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BasePage {

/********* TextField e TextArea *********/
public void escrever(By by, String text) {
  getDriver().findElement(by).clear();
  getDriver().findElement(by).sendKeys(text);
}

public void escrever(String id_campo, String text) {
 escrever(By.id(id_campo), text);
}

public String obterValorCampo(String id_campo) {
  return getDriver().findElement(By.id(id_campo)).getAttribute("value");
}

/********* Radio e Check *********/
public void clicarRadioOuCheckbox(By by) {
  getDriver().findElement(by).click();
}

public void clicarRadioOuCheckbox(String id) {
  clicarRadioOuCheckbox(By.id(id));
}

public boolean isRadioOuCheckboxMarcado(String id) {
  return getDriver().findElement(By.id(id)).isSelected();
}

/********* Combo *********/
public void selecionarCombo(String id, String valor) {
  /**
   * É possível selecionar um item do combo através de mais de um meio:
   * índice,
   * value,
   * visibleText,
   * etc.
   * combo.selectByIndex(3);
   * combo.selectByValue("superior");
   */
  WebElement element = getDriver().findElement(By.id(id));
  Select combo = new Select(element);
  combo.selectByVisibleText(valor);
}

public void deselecionarCombo(String id, String valor) {
  WebElement element = getDriver().findElement(By.id(id));
  Select combo = new Select(element);
  combo.deselectByVisibleText(valor);
}

public String obterValorCombo(String id) {
  WebElement element = getDriver().findElement(By.id(id));
  Select combo = new Select(element);
  return combo.getFirstSelectedOption().getText();
}

public List<String> obterValoresCombo(String string) {
  WebElement element = getDriver().findElement(By.id("elementosForm:esportes"));
  Select combo = new Select(element);
  List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
  List<String> valores = new ArrayList<String>();
  for (WebElement opcao : allSelectedOptions) {
    valores.add(opcao.getText());
  }
  return valores;
}

public int obterQuantidadeDeOpcoesCombo(String id) {
  WebElement element = getDriver().findElement(By.id(id));
  Select combo = new Select(element);
  List<WebElement> options = combo.getOptions();
  return options.size();
}

public boolean verificaOpcaoCombo(String id, String opcao) {
  boolean encontrou = false;
  WebElement element = getDriver().findElement(By.id(id));
  Select combo = new Select(element);
  List<WebElement> options = combo.getOptions();
  for (WebElement option : options) {
    if (option.getText().equals(opcao)) {
      encontrou = true;
      break;
    }
  }
  return encontrou;
}

public void selecionarComboPrime(String radical, String valor) {
  clicarRadioOuCheckbox(By.xpath("//*[@id='"+radical+"']/../..//span"));
  clicarRadioOuCheckbox(By.xpath("//*[@id='"+radical+"_items']//li[.='"+valor+"']"));
}

/********* Botao *********/
public void clicarBotao(String id_campo) {
  getDriver().findElement(By.id(id_campo)).click();
}

public String obterValueElemento(String string) {
  return getDriver().findElement(By.id(string)).getAttribute("value");
}

/********* Link *********/
public void clicarLink(String link) {
  getDriver().findElement(By.linkText(link)).click();
}

/********* Textos *********/
public String obterTexto(By by) {
  return getDriver().findElement(by).getText();
}

public String obterTexto(String id) {
  return obterTexto(By.id(id));
}

/********* Alerts *********/
public String alertaObterTexto() {
  Alert alert = getDriver().switchTo().alert();
  return alert.getText();
}

public String alertaObterTextoEAceitar() {
  Alert alert = getDriver().switchTo().alert();
  String texto = alert.getText();
  alert.accept();
  return texto;
}

public String alertaObterTextoENegar() {
  Alert alert = getDriver().switchTo().alert();
  String texto = alert.getText();
  alert.dismiss();
  return texto;
}

public void alertaEscrever(String string) {
  Alert alert = getDriver().switchTo().alert();
  alert.sendKeys(string);
  alert.accept();
}

/********* Frames e Janelas *********/
public void entrarFrame(String string) {
  getDriver().switchTo().frame(string);
}

public void sairFrame() {
  getDriver().switchTo().defaultContent();
}

public void trocaJanela(String string) {
  getDriver().switchTo().window(string);
}

/********* JavaScript *********/
public Object executarJS(String cmd, Object... params) {
  JavascriptExecutor js = (JavascriptExecutor) getDriver();
  return js.executeScript(cmd, params);
}

/********* Tabela *********/
public void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela) {
  // procurar coluna do registro
  WebElement tabela = getDriver().findElement(By.xpath("//*[@id='elementosForm:tableUsuarios']"));
  int idColuna = obterIndiceColuna(colunaBusca, tabela);
  
  // encontrar a linha do registro
  int idLinha = obterIndiceLinha(valor, tabela, idColuna);
  
  // encontrar a coluna do botão
  int idColunaBotao = obterIndiceColuna(colunaBotao, tabela);

  // clicar no botão da celula encontrada
  WebElement celula = getDriver().findElement(By.xpath(".//tr["+idLinha+"]/td["+idColunaBotao+"]"));
  celula.findElement(By.xpath(".//input")).click();
}

protected int obterIndiceLinha(String valor, WebElement tabela, int idColuna) {
  List<WebElement> linhas = tabela.findElements(By.xpath("./tbody/tr/td["+idColuna+"]"));
  int idLinha = -1;
  for (int i = 0; i < linhas.size(); i++) {
    if (linhas.get(i).getText().equals(valor)) {
      idLinha = i + 1;
      break;
    }
  }
  return idLinha;
}

protected int obterIndiceColuna(String coluna, WebElement tabela) {
  List<WebElement> colunas = tabela.findElements(By.xpath(".//th"));
  int idColuna = -1;
  for (int i = 0; i < colunas.size(); i++) {
    if (colunas.get(i).getText().equals(coluna)) {
      idColuna = i + 1;
      break;
    }
  }
  return idColuna;
}

}

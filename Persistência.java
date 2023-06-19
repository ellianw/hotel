// buscar dados persistidos
if (a.abrirLeitura() ) {
  dados = a.lerLinha();
  while (dados != null) {
    dia = Integer.parseInt(posicaoCSV(dados,1)) + 1;
    sucosTotal = Double.valueOf(posicaoCSV(dados,2));
    laranjasConsumidas = Integer.parseInt(posicaoCSV(dados,3)); 
    laranjas = Integer.parseInt(posicaoCSV(dados,4));
    lucroTotal = Double.valueOf(posicaoCSV(dados,5));
    dias = Integer.parseInt(posicaoCSV(dados,6)) + 1;
    dados = a.lerLinha();
  }
  a.fecharArquivo();
}

// persistir os dados
a.abrirEscrita();
dados = dia + ";" +
  sucosTotal + ";" +
  laranjasConsumidas + ";" +
  laranjas + ";" +
  lucroTotal + ";" +
  dias;
a.escreverLinha(dados);
a.fecharArquivo(); 

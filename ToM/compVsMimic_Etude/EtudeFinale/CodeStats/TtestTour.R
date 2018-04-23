# Verifie si la moyenne de chaque colonne est significativement superieur à 0

TtestTour <- function(xlsFile, sheet){
  
  my_array<- read.xlsx(xlsFile,sheetIndex = sheet)
  noms <-names(my_array)
  attach(my_array)
  i = 1;
  for(xol in my_array)
  {
    print(noms[i])
    t<- wilcox.test(xol, mu=0, alternative = "greater", conf.level = 0.95)
      #t.test(xol, mu=0, alternative = "greater", conf.level = 0.95)
    print(t)
    i = i+1
  }
  
}

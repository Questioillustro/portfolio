import sys
from PyQt5.QtWidgets import QAction
from PyQt5.QtGui import QIcon

def buildMenu(menubar, self):
    openFile = QAction(QIcon('open.png'), 'Open', self)
    openFile.setShortcut('Ctrl+O')
    openFile.setStatusTip('Open new File')
    openFile.triggered.connect(self.showDialog)
    
    fileMenu = menubar.addMenu('&File')
    fileMenu.addAction(openFile) 

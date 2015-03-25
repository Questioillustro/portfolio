import sys
from PyQt5.QtWidgets import (QWidget, QLabel, QLineEdit, QListWidget, QPlainTextEdit,
    QTextEdit, QGridLayout, QApplication, QHBoxLayout, QCheckBox, QComboBox, QPushButton,
    QTableWidget, QTableWidgetItem, QRadioButton, QAbstractItemView)
import db.query as query
import qdarkstyle
from PySide import QtGui
import re

class Example(QWidget):
    
    def __init__(self):
        super().__init__()
        
        self.initUI()
        
        
    def initUI(self):
        # Search area grid
        self.keyword_rad = QRadioButton('Keyword', self)
        self.description_rad = QRadioButton('Description', self)
        self.code_rad = QRadioButton('Code', self)
        
        self.language_l = QLabel('Language')
        self.language_select = QComboBox()
        self.language_select.addItem('All')
        langs = query.get_all_languages()
        for l in langs:
            self.language_select.addItem(l[0])

        self.search_l = QLabel('Search String')
        self.search_txt = QLineEdit()
        self.search_btn = QPushButton('Search')
        self.search_btn.clicked.connect(self.searchDB)

        # Table for results
        self.display_tbl = QTableWidget(0,5)
        self.display_tbl.setHorizontalHeaderLabels(['Name', 'Language', 'Package', 'Path'])
        self.display_tbl.setSelectionBehavior(QAbstractItemView.SelectRows)
        self.display_tbl.cellClicked.connect(self.file_selected)
        self.display_tbl.hideColumn(4)
        self.display_tbl.horizontalHeader().setStretchLastSection(True)
        
        # Plain text for code display
        self.display_txt = QPlainTextEdit()
        
        self.grid_search = QGridLayout()
        self.grid_search.setSpacing(10)

        # Build the search panel grid
        self.grid_search.addWidget(self.keyword_rad, 3, 1)
        self.grid_search.addWidget(self.description_rad, 3, 2)
        self.grid_search.addWidget(self.code_rad, 3, 3)
                
        self.grid_search.addWidget(self.language_l, 1, 0)
        self.grid_search.addWidget(self.language_select, 2, 0)
        
        self.grid_search.addWidget(self.search_l, 1, 1)
        self.grid_search.addWidget(self.search_txt, 2, 1, 1, 4)
        self.grid_search.addWidget(self.search_btn, 2, 5)
        
        self.grid_search.addWidget(self.display_tbl, 4, 0, 1, 3)
        self.grid_search.addWidget(self.display_txt, 4, 3, 1, 3)
        
        self.setLayout(self.grid_search) 
        
        self.setGeometry(200, 200, 800, 400)
        self.setWindowTitle('Review')    
        self.show()

    def searchDB(self, event):

        # Execute search
        search = self.search_txt.text()
        results = [];
        if self.keyword_rad.isChecked():
            results += query.get_all_by_keyword(search)

        #if self.description_rad.isChecked():
            #results += query.find_by
            
        if self.code_rad.isChecked():
            results += query.find_in_code(search)

        # Display the results
        self.display_tbl.setRowCount(len(results))
        for i, r in enumerate(results):
            nameItem = QTableWidgetItem(r['name'])
            pathItem = QTableWidgetItem(r['path'])
            contentItem = QTableWidgetItem(r['content'])
            
            lang = query.get_language_by_id(r['languageid'])
            langItem = QTableWidgetItem(lang)

            package = query.get_all_by_package(r['packageid'])
            pkgItem = QTableWidgetItem(package)
            
            self.display_tbl.setItem(i, 0, nameItem)  
            self.display_tbl.setItem(i, 1, langItem)
            self.display_tbl.setItem(i, 2, pkgItem)
            self.display_tbl.setItem(i, 3, pathItem)
            self.display_tbl.setItem(i, 4, contentItem)

    def file_selected (self, row, column):
        content = self.display_tbl.item(row, 4).text();
        tabs = re.compile('[\t]')
        content = re.sub(tabs,'    ', content)
        self.display_txt.setPlainText(content)
    
if __name__ == '__main__':
    
    app = QApplication(sys.argv)
    app.setStyleSheet(qdarkstyle.load_stylesheet())
    ex = Example()
    sys.exit(app.exec_())

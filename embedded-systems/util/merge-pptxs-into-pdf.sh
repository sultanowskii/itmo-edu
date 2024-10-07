#!/bin/bash

PPTX_DIR=pptxs
PDF_DIR=pdfs
MERGED_PDF=merged.pdf

soffice --headless --convert-to pdf *.pptx

rm ${MERGED_PDF}

pdfunite *.pdf ${MERGED_PDF}

mkdir ${PPTX_DIR}
mv *.pptx ${PPTX_DIR}

mkdir ${PDF_DIR}
mv *.pdf ${PDF_DIR}
mv ${PDF_DIR}/${MERGED_PDF} .

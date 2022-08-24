# CSC2002S Assignment 2 Makefile
# Nkosinathi Ntuli
# 2022/08/24

JAVAC=/usr/bin/javac

.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= HungryWordMover.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/*.class

run: $(CLASS_FILES)
	java -cp $(BINDIR) HungryWordMover $(input)

docs:
	javadoc -d $(DOCDIR)/ $(SRCDIR)/*.java

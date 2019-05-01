all: mt7688_pinmux mt7688_refclk

mt7688_pinmux : pinmux.o
	${CC} pinmux.c -o $@

mt7688_refclk : refclk.o
	${CC} refclk.c -o $@

install:
	cp mt7688_pinmux /usr/sbin
	cp mt7688_refclk /usr/sbin

clean:
	rm -f mt7688_pinmux mt7688_refclk *.o

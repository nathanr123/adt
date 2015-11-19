
hex6x -order L bootimage.rmd pcieboot_helloworld_evm6678l.out(inputfile)
Bttbl2Hfile bootimage.btbl(st1 outfile) bootimage.h(outfile) bootimage.bin(outfile)
hfile2array bootimage.h(st2 outfile) outheader.h(outfile) bootCode(variable name)

hex6x -order L bootimage.rmd pcieboot_helloworld_evm6678l.out
Bttbl2Hfile bootimage.btbl bootimage.h bootimage.bin
hfile2array bootimage.h outheader.h bootCode
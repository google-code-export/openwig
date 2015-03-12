### About ###

OpenWIG is an attempt to develop a j2me (java micro edition) mobile application (midlet) with same capabilities as [Wherigo](http://www.wherigo.com/) player.

It achieves this by implementing Lua extensions used in Wherigo source files.

**Please, [report bugs](http://code.google.com/p/openwig/issues/entry)!** I fix them, and often rather soon! But there is nothing worse than googling for "openwig" and reading "oh, so i tried it, but it didn't work with my cartridge, maybe next version" and not know what the problem is.

**Prosím, všechny chyby hlašte!** Já je pak opravuju, a většinou celkem rychle. Není nic horšího než dát do googlu "openwig" a najít "tak jsem to zkusil, ale nefungovalo to, no snad další verze" a netušit co je za problém.

_OpenWIG is not in any way affiliated with Groundspeak Inc._
And it's quite possible that Wherigo is a trademark of Groundspeak. I didn't check. But that's not the point anyway.

### What's new ###

Version **0.4.3** was released. It incorporates many bugfixes collected over the last year. No particular new features are included, but many more cartridges should be playable now.

The OTA link is http://openwig.googlecode.com/files/OpenWIG.jad

### How to install ###

see InstallationGuide

### How to use ###

_(coming soon)_

### License ###

The project source codes are released under GNU GPLv2.

### Credits ###

[Kahlua](http://code.google.com/p/kahlua), a Lua virtual machine for J2ME - is the heart of OpenWIG

[BLUElet](http://www.benhui.net/bluetooth), bluetooth device selection dialog
(link is dead at the moment)

[HandyGeocaching](http://hg.destil.cz/), geocacher's swiss army midlet - provided us with NMEA parser and a few utility navigation-related functions

Many thanks to [Misch](http://ati.land.cz/) for decoding Wherigo-related formats and providing the [GWC service](http://ati.land.cz/gps/wherigo/)
Welcome to PlaCS, the Player Character Scrambler!

Motivation: Generate increased difficulty for intelligence agencies to correctly identify 
specific individuals through such items as writing style, profile picture, username, etc. through 
the deployment of a collective effort of active obfuscation. While we already have encrypted private 
conversation, the idea here is very much about publicly sharing data (with strangers), and make it 
difficult to interpret for machine-learning algorithms. One example would be if individuals gravitate
towards some sort of discrete, unspoken protocol relating to the exchange of seeds.

Features:
- Username scrambler: generate random username (with "JohnDoe" root). There are only about 13'000 
possibilities, such that only a limited number of JohnDoe names are being circulated, the idea being 
of generating incorrect association patterns (JohPDok0000 might be a brazilian guy on RandomPorn.gov 
and a german guy on SweatyAutists.org)
- Password scrambler: for convenience, randomly generated passwords are probably safer then human 
generated passwords (for the most part).
- Picture scrambler: generate a random array of colors organized into a square grid of pixels. The 
"seed" function is interesting here, because two random people could share the same garbled mess 
as their profile picture, thus creating an association, which might be incorrect.
- Message scrambler: transform your message into a garbled mess, which might or might not be understood 
properly by your (intended and unintended) audience.
- One-liner: by keeping clicking on the arrow while the input text field empty, you can generate a 
one-liner, the idea being that you should post these one-liners on your social medias once every 
few hour to obfuscate which of your posts are useful, and which aren't.
- Completely off-line, completely open-source, competely free: we really shouldn't thrust anything 
we don't compile ourselves.  

Future:
- Rework the password feature, my understanding has become more sophisticated.
- Include seed selection for the text scrambler
- Better language processing in general.
- More "random" tools, with ability to select a seed.
- Create the PlaCU, the Player Character Unscrambler, in particular for the sake of processing seed 
numbers.
- Create a "word shifter", where, based on a seed, every public word is offset from its intended 
private word. It might be a regular offset, or something more sophisticated. The danger here is that 
the conversation still needs to be happening publicly, that's the whole point.

Installation/Run instructions:

1) Jar: to run a jar, you may simply click on it, as long as you have java installed. Otherwise 
in linux you may run the command "java -jar PlayerCharacterScrambler.jar" from the command line.

2) Safety first: run these commands from the terminal 
(from within the PlayerCharacterScrambler directory)

   # cd <your path for PlayerCharacterScrambler>
   # javac -d compiled/ src/*/*.java
   # cd compiled
   # jar cvfm ../PlaCS.jar ../src/META-INF/MANIFEST.MF */*.class
   # cd ..
   # java -jar PlaCS.jar

Once the jar has been created you can revert back to 1) in order to launch the jar in the future.









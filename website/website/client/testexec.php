<?php
echo "<h2>PHP is Fun!</h2>";
echo "Hello world!<br>";
echo "I'm about to learn PHP!<br>";
echo "This ", "string ", "was ", "made ", "with multiple parameters.";
shell_exec("mvn package");
$myfile = fopen("hash.txt", "w") or die("Unable to open file!");
$txt = shell_exec("java -cp /home/marco/ICHack19/website/website/client/target/1.0-1.0-SNAPSHOT.jar Main image.jpg");
fwrite($myfile, $txt);
fclose($myfile);
echo shell_exec("java -cp /home/marco/ICHack19/website/website/client/target/1.0-1.0-SNAPSHOT.jar Main image.jpg");
?> 

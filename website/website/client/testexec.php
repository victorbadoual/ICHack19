<?php
shell_exec("mvn package");
echo shell_exec("java -cp /home/marco/ICHack19/website/website/client/target/1.0-1.0-SNAPSHOT.jar Main image.png");
?>

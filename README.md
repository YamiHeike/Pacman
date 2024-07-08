<h1>Pacman - Java GUI</h1>
A Java Swing GUI project for my Computer Science studies in Polish-Japanese Academy of Information Technology.
<h3>Assesment description</h3>

<h3>The project had several limitations:</h3>
<ul>
  <li>It needs to be implemented in Java Swing</li>
  <li>Only several features of AWT allowed: Component/Container class, events, Dimension, Font - No Graphics/paintComponent methods</li>
  <li>In the game, opponents create power-ups (e.g., +50% movement speed, etc.) that the player can collect with a probability of 25% every 5 seconds. Implement at least 5 different power-ups. When the program starts, it displays a main menu consisting of the options: New Game, Highscores, Exit</li>
  <li>When starting a new game, the player is asked about the size of the board they want to play on. At least 5 different boards of different sizes must be implemented. After choosing the board size, the generated game board is displayed in a new window.</li>
  <li>A fully functional graphical interface must be provided. The CLI may only be used as informational help for the developer, but no interaction or information display to users should occur there.</li>
  <li>Images must be scalable</li>
  <li>During the game, a score counter, time counter, life counter, and other necessary graphical interface elements that are updated live during gameplay must be visible.</li>
  <li>The project must use graphical files and achieve a consistent visual effect throughout the application, including all application windows. Character movement and tasks should be implemented through simple frame-by-frame animations (e.g., movement animation, eating animation, etc.).</li>
  <li>Animations must be implemented independently based on threads, not through GIF files. All time-related issues must be handled with threads (do not use the Timer, Executor, or other classes). Thoughtful and correct thread synchronization must be ensured. Different functionalities cannot be combined into a single thread.</li>
  <li>The game proceeds according to the above rules. It must be possible to interrupt the game at any time, which will return to the main menu. After the game ends, the player is asked for the name under which they want to be saved in the ranking in a new window. Ensure the ranking's persistence using the Serializable interface and file saving. After selecting the ranking option from the main menu, it is displayed to the user. The ranking should be implemented using the JList component. Since the ranking window can be relatively large, scroll bars should be provided.</li>
  <li>No external libraries or WYSIWYG tools allowed</li>

</ul>

I was awarded <bold>maximum score of 20/20</bold> as I managed to meet all of the requirements without using any of the forbidden functionalities and technologies in a very limited time.
It was a main purpose of this task.

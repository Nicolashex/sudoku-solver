# Sudoku solver
This project was made for the High Performance Computing class. The aim is to create a program that can solve a given sudoku but using treads in the process.
This program was tested with a computer running with a Ryzen 1400 with a clockspeed of 3.2 Ghz and 4 cores/ 8 threads.
The results given may vary depending on the computer wich is tested. 

<table>
    <thead>
      <tr>
        <th>Number of threads</th>
        <th>Average time in nano seconds</th>
        <th>SpeedUp</th>
        <th>Efficency</th>
      </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>220508</td>
            <td>1</td>
            <td>1</td>
        </tr>
        <tr>
            <td>2</td>
            <td>195762</td>
            <td>1,126409682</td>
            <td>0,563204841</td>
        </tr>
        <tr>
            <td>3</td>
            <td>176500</td>
            <td>1,249335367</td>
            <td>0,416445122</td>
        </tr>
        <tr>
            <td>4</td>
            <td>169992</td>
            <td>1,297162768</td>
            <td>0,324290692</td>
        </tr>
        <tr>
            <td>5</td>
            <td>163715</td>
            <td>1,346896584</td>
            <td>0,269379317</td>
        </tr>
        <tr>
            <td>6</td>
            <td>186223</td>
            <td>1,184105085</td>
            <td>0,197350847</td>
        </tr>
        <tr>
            <td>7</td>
            <td>183362</td>
            <td>1,202584218</td>
            <td>0,171797745</td>
        </tr>
        <tr>
            <td>8</td>
            <td>182469</td>
            <td>1,208465073</td>
            <td>0,151058134</td>
        </tr>
        <tr>
            <td>9</td>
            <td>189154</td>
            <td>1,165758438</td>
            <td>0,129528715</td>
        </tr>        
    </tbody>
  </table>

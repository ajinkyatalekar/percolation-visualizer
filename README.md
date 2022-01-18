# Percolation Visualizer

## Description
A visualizer for the percolation problem. Uses *Weighted Union Find with Path Compression* for solving the problem and *StdDraw* from *alg4.jar* to draw. Includes real-time flow which updates filled sites after every new site is opened.  

**NEW:** Interactive mode where you can open and fill sites on the grid using your mouse.

## Visuals
<!-- <img src="https://user-images.githubusercontent.com/91043799/149831257-e0183287-bb04-482d-9bcd-7d3c71bdb3f7.jpg" width=40% height=40%>
<img src="https://user-images.githubusercontent.com/91043799/149831623-f8e858f7-f219-4c59-9951-82740e45d98d.jpg" width=40% height=40%> -->
<p align = "center">
<img src="https://user-images.githubusercontent.com/91043799/149837107-c046849e-f399-4e63-8a4d-212f469214b9.gif" width=40% height=40%>
<img src="https://user-images.githubusercontent.com/91043799/149838081-1e25760c-60dd-4683-ba34-95645e1e70a6.gif" width=40% height=40%>
</p>  

**Note:** Real-time is now way faster, the shown gif is from a previous version.

## Installation
Yank the entire directory.  
**External libraries used**: Uses *alg4.jar* which is included in  <code>/lib</code>. Add it to the referenced libraries.  

## Usage
Console inputs:
1. Run  <code>PercolationVisualizer.java</code>.
2. Enter <code>z</code> in console for normal randomized site opening mode; or <code>x</code> for interactive mode.  
3. Steps for different modes:  
3.1 (Normal Mode) Enter two *ints* seperated by a  whitespace. The first corresponds to size of the grid, the second to number of trials. Open the newly generated window.  
3.2 (Interactive Mode) Enter one *int* for the size of grid and open the newly generated window.
  
After each trial, the percent of sites open when percolation happens are logged and displayed for a brief moment (default: *1.5s*, can be changed). After the end of all trials, the mean percent of open sites, standard deviation, and other data are shown.  
  
In the interactive mode, keep the sample size low.  

**Important:** You can change <code>*boolean* realTimeFlow</code> to turn real time flow off (on by default).

## Author
**Ajinkya Talekar** | [ajinkyatalekar.github.io](https://ajinkyatalekar.github.io)

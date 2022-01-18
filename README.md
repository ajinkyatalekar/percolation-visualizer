# Percolation Visualizer

## Description
A visualizer for the percolation problem. Uses *Weighted Union Find with Path Compression* for solving the problem and *StdDraw* from *alg4.jar* to draw.  
  
Includes real-time flow which updates filled sites after every new site is opened.

## Visuals
<!-- <img src="https://user-images.githubusercontent.com/91043799/149831257-e0183287-bb04-482d-9bcd-7d3c71bdb3f7.jpg" width=40% height=40%>
<img src="https://user-images.githubusercontent.com/91043799/149831623-f8e858f7-f219-4c59-9951-82740e45d98d.jpg" width=40% height=40%> -->
<p align = "center">
<img src="https://user-images.githubusercontent.com/91043799/149837107-c046849e-f399-4e63-8a4d-212f469214b9.gif" width=40% height=40%>
<img src="https://user-images.githubusercontent.com/91043799/149838081-1e25760c-60dd-4683-ba34-95645e1e70a6.gif" width=40% height=40%>
</p>  

**Note:** Real-time is now way faster, the shown gif is from a previous version.

## Installation
Yank the entire directory and run  <code>PercolationVisualizer.java</code>  
**External libraries used**: Uses *alg4.jar* which is included in  <code>/lib</code>. Add it to the referenced libraries.  

## Usage
Enter two *ints* seperated by a  whitespace. The first corresponds to size of the grid, the second to number of trials.  
As an example, <code>*Sample size and trials*: **20 10**</code> will make a 20 by 20 grid and run 10 trials on it.  
  
<img src="https://user-images.githubusercontent.com/91043799/149835193-8fa8560d-ab67-4d15-a7bb-0bccb75c883d.png" width=40% height=40%>  
  
After each trial, the percent of sites open when percolation happens are logged and displayed for a brief moment (default: *1.5s*, can be changed). After the end of all trials, the mean percent of open sites, standard deviation, and other data are shown.

**Important:** You can change <code>*boolean* realTimeFlow</code> to turn on real time flow.

## Author
**Ajinkya Talekar** | [ajinkyatalekar.github.io](https://ajinkyatalekar.github.io)

// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package adder

import chisel3._
import chisel3.util._


/** 
  * Half Adder Class 
  * 
  * Your task is to implement a basic half adder as presented in the lecture.
  * Each signal should only be one bit wide (inputs and outputs).
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class HalfAdder extends Module{
  
  val io = IO(new Bundle {
    /* 
     * TODO: Define IO ports of a half adder as presented in the lecture
     */
      val A = Input(UInt(1.W))
      val B = Input(UInt(1.W))
      val S = Output(UInt(1.W))
      val C = Output(UInt(1.W))
    })

  /* 
   * TODO: Describe output behaviour based on the input values
   */
  var signal = io.S //pointer to io.S
  signal :=  io.A ^ io.B //assign value to io.S
  signal = io.C//pointer to io.C
  signal := io.A & io.B//assign value to io.C
}

/** 
  * Full Adder Class 
  * 
  * Your task is to implement a basic full adder. The component's behaviour should 
  * match the characteristics presented in the lecture. In addition, you are only allowed 
  * to use two half adders (use the class that you already implemented) and basic logic 
  * operators (AND, OR, ...).
  * Each signal should only be one bit wide (inputs and outputs).
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class FullAdder extends Module{

  val io = IO(new Bundle {
    /* 
     * TODO: Define IO ports of a half adder as presented in the lecture
     */
      val A = Input(UInt(1.W))
      val B = Input(UInt(1.W))
      val Ci = Input(UInt(1.W))
      val S = Output(UInt(1.W))
      val C = Output(UInt(1.W))
    })

  /* 
   * TODO: Describe output behaviour based on the input values
   */
  val half1 = Module(new HalfAdder)
  val half2 = Module(new HalfAdder)
  half1.io.A := io.A
  half1.io.B := io.B
  half2.io.A := io.Ci
  half2.io.B := half1.io.S

  var signal = io.S //pointer to io.S
  signal := half2.io.S //assign value to io.S
  signal = io.C//pointer to io.C
  signal := half2.io.C | half1.io.C//assign value to io.C
  }



/** 
  * 4-bit Adder class 
  * 
  * Your task is to implement a 4-bit ripple-carry-adder. The component's behaviour should 
  * match the characteristics presented in the lecture.  Remember: An n-bit adder can be 
  * build using one half adder and n-1 full adders.
  * The inputs and the result should all be 4-bit wide, the carry-out only needs one bit.
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class FourBitAdder extends Module{

  val io = IO(new Bundle {
    /* 
     * TODO: Define IO ports of a half adder as presented in the lecture
     */
      val A = Input(UInt(4.W))
      val B = Input(UInt(4.W))
      val S = Output(UInt(4.W))
      val C = Output(UInt(1.W))
    })

  /* 
   * TODO: Describe output behaviour based on the input values
   */
  val half = Module(new HalfAdder)
  val sum1 = Module(new FullAdder)
  val sum2 = Module(new FullAdder)
  val sum3 = Module(new FullAdder)

  half.io.A := io.A(0)
  half.io.B := io.B(0)
  sum1.io.A := io.A(1)
  sum1.io.B := io.B(1)
  sum1.io.Ci := half.io.C
  sum2.io.A := io.A(2)
  sum2.io.B := io.B(2)
  sum2.io.Ci := sum1.io.C
  sum3.io.A := io.A(3)
  sum3.io.B := io.B(3)
  sum3.io.Ci := sum2.io.C

  var signal = io.S //pointer to io.S
  signal := Cat(sum3.io.S, sum2.io.S, sum1.io.S, half.io.S)//assign value to io.S
  signal = io.C//pointer to io.C
  signal := sum3.io.C//assign value to io.C
  }



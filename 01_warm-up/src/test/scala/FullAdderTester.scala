// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package adder

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


/** 
  * Full adder tester
  * Use the truth table from the exercise sheet to test all possible input combinations and the corresponding results exhaustively
  */
class FullAdderTester extends AnyFlatSpec with ChiselScalatestTester {

  "FullAdder" should "work" in {
    test(new FullAdder).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
       for (A <- 0 to 1) {
        for (B <- 0 to 1) {
          for (Ci <- 0 to 1) {
            val expec_sum = (A ^ B) ^ Ci
            val expec_carry = (A & B) | ((A ^ B) & Ci)
            dut.io.A.poke(A.U)
            dut.io.B.poke(B.U)
            dut.io.Ci.poke(Ci.U)
            dut.io.S.expect(expec_sum.U)
            dut.io.C.expect(expec_carry.U)
          }
        }
      } 
          /*dut.io.a.poke(...)
           *dut.io.b.poke(...)
           *dut.io.ci.poke(...)
           *dut.io.s.expect(...)
           *dut.io.co.expect(...)
           *...
           *TODO: Insert your test cases
           */

        }
    } 
}


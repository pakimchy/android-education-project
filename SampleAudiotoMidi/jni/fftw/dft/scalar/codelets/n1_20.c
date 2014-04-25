/*
 * Copyright (c) 2003, 2007-8 Matteo Frigo
 * Copyright (c) 2003, 2007-8 Massachusetts Institute of Technology
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/* This file was automatically generated --- DO NOT EDIT */
/* Generated on Sun Jul 12 06:37:30 EDT 2009 */

#include "codelet-dft.h"

#ifdef HAVE_FMA

/* Generated by: ../../../genfft/gen_notw -fma -reorder-insns -schedule-for-pipeline -compact -variables 4 -pipeline-latency 4 -n 20 -name n1_20 -include n.h */

/*
 * This function contains 208 FP additions, 72 FP multiplications,
 * (or, 136 additions, 0 multiplications, 72 fused multiply/add),
 * 86 stack variables, 4 constants, and 80 memory accesses
 */
#include "n.h"

static void n1_20(const R *ri, const R *ii, R *ro, R *io, stride is, stride os, INT v, INT ivs, INT ovs)
{
     DK(KP951056516, +0.951056516295153572116439333379382143405698634);
     DK(KP559016994, +0.559016994374947424102293417182819058860154590);
     DK(KP618033988, +0.618033988749894848204586834365638117720309180);
     DK(KP250000000, +0.250000000000000000000000000000000000000000000);
     INT i;
     for (i = v; i > 0; i = i - 1, ri = ri + ivs, ii = ii + ivs, ro = ro + ovs, io = io + ovs, MAKE_VOLATILE_STRIDE(is), MAKE_VOLATILE_STRIDE(os)) {
	  E T1Y, T1Z, T1W, T1V;
	  {
	       E T1d, TP, TD, T7, T3b, T2N, T2f, T1R, T2U, TB, T2P, T2A, T3d, T37, T3j;
	       E TJ, T2n, T1b, T1T, T1y, T2b, T2h, T1j, T2V, Tm, T2O, T2H, T3c, T34, T1e;
	       E T1f, T3i, TG, T2m, T10, T1S, T1J, T28, T2g;
	       {
		    E T4, T1N, T3, T2L, TN, T5, T1O, T1P, T1h, T1i;
		    {
			 E T1, T2, TL, TM;
			 T1 = ri[0];
			 T2 = ri[WS(is, 10)];
			 TL = ii[0];
			 TM = ii[WS(is, 10)];
			 T4 = ri[WS(is, 5)];
			 T1N = T1 - T2;
			 T3 = T1 + T2;
			 T2L = TL + TM;
			 TN = TL - TM;
			 T5 = ri[WS(is, 15)];
			 T1O = ii[WS(is, 5)];
			 T1P = ii[WS(is, 15)];
		    }
		    {
			 E T1o, Tp, T2u, T13, T14, Ts, T2v, T1r, Tx, T1t, Tw, T2x, T18, Ty, T1u;
			 E T1v;
			 {
			      E Tq, Tr, T1p, T1q;
			      {
				   E Tn, To, T11, T12;
				   Tn = ri[WS(is, 8)];
				   {
					E TO, T6, T2M, T1Q;
					TO = T4 - T5;
					T6 = T4 + T5;
					T2M = T1O + T1P;
					T1Q = T1O - T1P;
					T1d = TO + TN;
					TP = TN - TO;
					TD = T3 + T6;
					T7 = T3 - T6;
					T3b = T2L + T2M;
					T2N = T2L - T2M;
					T2f = T1N + T1Q;
					T1R = T1N - T1Q;
					To = ri[WS(is, 18)];
				   }
				   T11 = ii[WS(is, 8)];
				   T12 = ii[WS(is, 18)];
				   Tq = ri[WS(is, 13)];
				   T1o = Tn - To;
				   Tp = Tn + To;
				   T2u = T11 + T12;
				   T13 = T11 - T12;
				   Tr = ri[WS(is, 3)];
				   T1p = ii[WS(is, 13)];
				   T1q = ii[WS(is, 3)];
			      }
			      {
				   E Tu, Tv, T16, T17;
				   Tu = ri[WS(is, 12)];
				   T14 = Tq - Tr;
				   Ts = Tq + Tr;
				   T2v = T1p + T1q;
				   T1r = T1p - T1q;
				   Tv = ri[WS(is, 2)];
				   T16 = ii[WS(is, 12)];
				   T17 = ii[WS(is, 2)];
				   Tx = ri[WS(is, 17)];
				   T1t = Tu - Tv;
				   Tw = Tu + Tv;
				   T2x = T16 + T17;
				   T18 = T16 - T17;
				   Ty = ri[WS(is, 7)];
				   T1u = ii[WS(is, 17)];
				   T1v = ii[WS(is, 7)];
			      }
			 }
			 {
			      E TH, T19, T1w, TI;
			      {
				   E Tt, T2w, T35, TA, T2z, T36, Tz, T2y;
				   TH = Tp + Ts;
				   Tt = Tp - Ts;
				   T19 = Tx - Ty;
				   Tz = Tx + Ty;
				   T2y = T1u + T1v;
				   T1w = T1u - T1v;
				   T2w = T2u - T2v;
				   T35 = T2u + T2v;
				   TI = Tw + Tz;
				   TA = Tw - Tz;
				   T2z = T2x - T2y;
				   T36 = T2x + T2y;
				   T2U = Tt - TA;
				   TB = Tt + TA;
				   T2P = T2w + T2z;
				   T2A = T2w - T2z;
				   T3d = T35 + T36;
				   T37 = T35 - T36;
			      }
			      {
				   E T1s, T29, T1x, T2a, T15, T1a;
				   T15 = T13 - T14;
				   T1h = T14 + T13;
				   T1i = T19 + T18;
				   T1a = T18 - T19;
				   T1s = T1o - T1r;
				   T29 = T1o + T1r;
				   T3j = TH - TI;
				   TJ = TH + TI;
				   T1x = T1t - T1w;
				   T2a = T1t + T1w;
				   T2n = T15 - T1a;
				   T1b = T15 + T1a;
				   T1T = T1s + T1x;
				   T1y = T1s - T1x;
				   T2b = T29 - T2a;
				   T2h = T29 + T2a;
			      }
			 }
		    }
		    {
			 E Ta, T1z, T2B, TS, TT, Td, T2C, T1C, Ti, T1E, Th, T2E, TX, Tj, T1F;
			 E T1G;
			 {
			      E Tb, Tc, T1A, T1B;
			      {
				   E TQ, TR, T8, T9;
				   T8 = ri[WS(is, 4)];
				   T9 = ri[WS(is, 14)];
				   T1j = T1h + T1i;
				   T1Y = T1h - T1i;
				   TQ = ii[WS(is, 4)];
				   TR = ii[WS(is, 14)];
				   Ta = T8 + T9;
				   T1z = T8 - T9;
				   Tb = ri[WS(is, 9)];
				   T2B = TQ + TR;
				   TS = TQ - TR;
				   Tc = ri[WS(is, 19)];
				   T1A = ii[WS(is, 9)];
				   T1B = ii[WS(is, 19)];
			      }
			      {
				   E Tf, Tg, TV, TW;
				   Tf = ri[WS(is, 16)];
				   TT = Tb - Tc;
				   Td = Tb + Tc;
				   T2C = T1A + T1B;
				   T1C = T1A - T1B;
				   Tg = ri[WS(is, 6)];
				   TV = ii[WS(is, 16)];
				   TW = ii[WS(is, 6)];
				   Ti = ri[WS(is, 1)];
				   T1E = Tf - Tg;
				   Th = Tf + Tg;
				   T2E = TV + TW;
				   TX = TV - TW;
				   Tj = ri[WS(is, 11)];
				   T1F = ii[WS(is, 1)];
				   T1G = ii[WS(is, 11)];
			      }
			 }
			 {
			      E TE, TY, T1H, TF;
			      {
				   E Te, T2D, T32, Tl, T2G, T33, Tk, T2F;
				   TE = Ta + Td;
				   Te = Ta - Td;
				   TY = Ti - Tj;
				   Tk = Ti + Tj;
				   T2F = T1F + T1G;
				   T1H = T1F - T1G;
				   T2D = T2B - T2C;
				   T32 = T2B + T2C;
				   TF = Th + Tk;
				   Tl = Th - Tk;
				   T2G = T2E - T2F;
				   T33 = T2E + T2F;
				   T2V = Te - Tl;
				   Tm = Te + Tl;
				   T2O = T2D + T2G;
				   T2H = T2D - T2G;
				   T3c = T32 + T33;
				   T34 = T32 - T33;
			      }
			      {
				   E T1D, T26, T1I, T27, TU, TZ;
				   TU = TS - TT;
				   T1e = TT + TS;
				   T1f = TY + TX;
				   TZ = TX - TY;
				   T1D = T1z - T1C;
				   T26 = T1z + T1C;
				   T3i = TE - TF;
				   TG = TE + TF;
				   T1I = T1E - T1H;
				   T27 = T1E + T1H;
				   T2m = TU - TZ;
				   T10 = TU + TZ;
				   T1S = T1D + T1I;
				   T1J = T1D - T1I;
				   T28 = T26 - T27;
				   T2g = T26 + T27;
			      }
			 }
		    }
	       }
	       {
		    E T1g, T3g, T3f, T2S, T2R, T2k, T2j;
		    {
			 E T2s, T2r, TC, T2Q;
			 T2s = Tm - TB;
			 TC = Tm + TB;
			 T1g = T1e + T1f;
			 T1Z = T1e - T1f;
			 T2r = FNMS(KP250000000, TC, T7);
			 ro[WS(os, 10)] = T7 + TC;
			 T2Q = T2O + T2P;
			 T2S = T2O - T2P;
			 {
			      E T2K, T2I, T2t, T2J;
			      T2K = FMA(KP618033988, T2A, T2H);
			      T2I = FNMS(KP618033988, T2H, T2A);
			      T2t = FNMS(KP559016994, T2s, T2r);
			      T2J = FMA(KP559016994, T2s, T2r);
			      ro[WS(os, 18)] = FMA(KP951056516, T2I, T2t);
			      ro[WS(os, 2)] = FNMS(KP951056516, T2I, T2t);
			      ro[WS(os, 6)] = FMA(KP951056516, T2K, T2J);
			      ro[WS(os, 14)] = FNMS(KP951056516, T2K, T2J);
			      T2R = FNMS(KP250000000, T2Q, T2N);
			 }
			 io[WS(os, 10)] = T2N + T2Q;
		    }
		    {
			 E T30, T2Z, TK, T3e;
			 TK = TG + TJ;
			 T30 = TG - TJ;
			 {
			      E T2T, T2X, T2Y, T2W;
			      T2T = FNMS(KP559016994, T2S, T2R);
			      T2X = FMA(KP559016994, T2S, T2R);
			      T2Y = FMA(KP618033988, T2U, T2V);
			      T2W = FNMS(KP618033988, T2V, T2U);
			      io[WS(os, 14)] = FMA(KP951056516, T2Y, T2X);
			      io[WS(os, 6)] = FNMS(KP951056516, T2Y, T2X);
			      io[WS(os, 18)] = FNMS(KP951056516, T2W, T2T);
			      io[WS(os, 2)] = FMA(KP951056516, T2W, T2T);
			      T2Z = FNMS(KP250000000, TK, TD);
			 }
			 ro[0] = TD + TK;
			 T3e = T3c + T3d;
			 T3g = T3c - T3d;
			 {
			      E T31, T39, T3a, T38;
			      T31 = FMA(KP559016994, T30, T2Z);
			      T39 = FNMS(KP559016994, T30, T2Z);
			      T3a = FNMS(KP618033988, T34, T37);
			      T38 = FMA(KP618033988, T37, T34);
			      ro[WS(os, 8)] = FMA(KP951056516, T3a, T39);
			      ro[WS(os, 12)] = FNMS(KP951056516, T3a, T39);
			      ro[WS(os, 16)] = FMA(KP951056516, T38, T31);
			      ro[WS(os, 4)] = FNMS(KP951056516, T38, T31);
			      T3f = FNMS(KP250000000, T3e, T3b);
			 }
			 io[0] = T3b + T3e;
		    }
		    {
			 E T24, T23, T1c, T2i;
			 T1c = T10 + T1b;
			 T24 = T10 - T1b;
			 {
			      E T3h, T3l, T3m, T3k;
			      T3h = FMA(KP559016994, T3g, T3f);
			      T3l = FNMS(KP559016994, T3g, T3f);
			      T3m = FNMS(KP618033988, T3i, T3j);
			      T3k = FMA(KP618033988, T3j, T3i);
			      io[WS(os, 12)] = FMA(KP951056516, T3m, T3l);
			      io[WS(os, 8)] = FNMS(KP951056516, T3m, T3l);
			      io[WS(os, 16)] = FNMS(KP951056516, T3k, T3h);
			      io[WS(os, 4)] = FMA(KP951056516, T3k, T3h);
			      T23 = FNMS(KP250000000, T1c, TP);
			 }
			 io[WS(os, 5)] = TP + T1c;
			 T2i = T2g + T2h;
			 T2k = T2g - T2h;
			 {
			      E T25, T2d, T2e, T2c;
			      T25 = FMA(KP559016994, T24, T23);
			      T2d = FNMS(KP559016994, T24, T23);
			      T2e = FNMS(KP618033988, T28, T2b);
			      T2c = FMA(KP618033988, T2b, T28);
			      io[WS(os, 17)] = FMA(KP951056516, T2e, T2d);
			      io[WS(os, 13)] = FNMS(KP951056516, T2e, T2d);
			      io[WS(os, 9)] = FMA(KP951056516, T2c, T25);
			      io[WS(os, 1)] = FNMS(KP951056516, T2c, T25);
			      T2j = FNMS(KP250000000, T2i, T2f);
			 }
			 ro[WS(os, 5)] = T2f + T2i;
		    }
		    {
			 E T1m, T1l, T1k, T1U;
			 T1k = T1g + T1j;
			 T1m = T1g - T1j;
			 {
			      E T2l, T2p, T2q, T2o;
			      T2l = FMA(KP559016994, T2k, T2j);
			      T2p = FNMS(KP559016994, T2k, T2j);
			      T2q = FNMS(KP618033988, T2m, T2n);
			      T2o = FMA(KP618033988, T2n, T2m);
			      ro[WS(os, 17)] = FNMS(KP951056516, T2q, T2p);
			      ro[WS(os, 13)] = FMA(KP951056516, T2q, T2p);
			      ro[WS(os, 9)] = FNMS(KP951056516, T2o, T2l);
			      ro[WS(os, 1)] = FMA(KP951056516, T2o, T2l);
			      T1l = FNMS(KP250000000, T1k, T1d);
			 }
			 io[WS(os, 15)] = T1d + T1k;
			 T1U = T1S + T1T;
			 T1W = T1S - T1T;
			 {
			      E T1n, T1L, T1M, T1K;
			      T1n = FNMS(KP559016994, T1m, T1l);
			      T1L = FMA(KP559016994, T1m, T1l);
			      T1M = FMA(KP618033988, T1y, T1J);
			      T1K = FNMS(KP618033988, T1J, T1y);
			      io[WS(os, 19)] = FMA(KP951056516, T1M, T1L);
			      io[WS(os, 11)] = FNMS(KP951056516, T1M, T1L);
			      io[WS(os, 7)] = FMA(KP951056516, T1K, T1n);
			      io[WS(os, 3)] = FNMS(KP951056516, T1K, T1n);
			      T1V = FNMS(KP250000000, T1U, T1R);
			 }
			 ro[WS(os, 15)] = T1R + T1U;
		    }
	       }
	  }
	  {
	       E T21, T1X, T20, T22;
	       T21 = FMA(KP559016994, T1W, T1V);
	       T1X = FNMS(KP559016994, T1W, T1V);
	       T20 = FNMS(KP618033988, T1Z, T1Y);
	       T22 = FMA(KP618033988, T1Y, T1Z);
	       ro[WS(os, 19)] = FNMS(KP951056516, T22, T21);
	       ro[WS(os, 11)] = FMA(KP951056516, T22, T21);
	       ro[WS(os, 7)] = FNMS(KP951056516, T20, T1X);
	       ro[WS(os, 3)] = FMA(KP951056516, T20, T1X);
	  }
     }
}

static const kdft_desc desc = { 20, "n1_20", {136, 0, 72, 0}, &GENUS, 0, 0, 0, 0 };

void X(codelet_n1_20) (planner *p) {
     X(kdft_register) (p, n1_20, &desc);
}

#else				/* HAVE_FMA */

/* Generated by: ../../../genfft/gen_notw -compact -variables 4 -pipeline-latency 4 -n 20 -name n1_20 -include n.h */

/*
 * This function contains 208 FP additions, 48 FP multiplications,
 * (or, 184 additions, 24 multiplications, 24 fused multiply/add),
 * 81 stack variables, 4 constants, and 80 memory accesses
 */
#include "n.h"

static void n1_20(const R *ri, const R *ii, R *ro, R *io, stride is, stride os, INT v, INT ivs, INT ovs)
{
     DK(KP587785252, +0.587785252292473129168705954639072768597652438);
     DK(KP951056516, +0.951056516295153572116439333379382143405698634);
     DK(KP250000000, +0.250000000000000000000000000000000000000000000);
     DK(KP559016994, +0.559016994374947424102293417182819058860154590);
     INT i;
     for (i = v; i > 0; i = i - 1, ri = ri + ivs, ii = ii + ivs, ro = ro + ovs, io = io + ovs, MAKE_VOLATILE_STRIDE(is), MAKE_VOLATILE_STRIDE(os)) {
	  E T7, T2Q, T3h, TD, TP, T1U, T2l, T1d, Tt, TA, TB, T2w, T2z, T2S, T35;
	  E T36, T3f, TH, TI, TJ, T15, T1a, T1b, T1s, T1x, T1W, T29, T2a, T2j, T1h;
	  E T1i, T1j, Te, Tl, Tm, T2D, T2G, T2R, T32, T33, T3e, TE, TF, TG, TU;
	  E TZ, T10, T1D, T1I, T1V, T26, T27, T2i, T1e, T1f, T1g;
	  {
	       E T3, T1Q, TN, T2O, T6, TO, T1T, T2P;
	       {
		    E T1, T2, TL, TM;
		    T1 = ri[0];
		    T2 = ri[WS(is, 10)];
		    T3 = T1 + T2;
		    T1Q = T1 - T2;
		    TL = ii[0];
		    TM = ii[WS(is, 10)];
		    TN = TL - TM;
		    T2O = TL + TM;
	       }
	       {
		    E T4, T5, T1R, T1S;
		    T4 = ri[WS(is, 5)];
		    T5 = ri[WS(is, 15)];
		    T6 = T4 + T5;
		    TO = T4 - T5;
		    T1R = ii[WS(is, 5)];
		    T1S = ii[WS(is, 15)];
		    T1T = T1R - T1S;
		    T2P = T1R + T1S;
	       }
	       T7 = T3 - T6;
	       T2Q = T2O - T2P;
	       T3h = T2O + T2P;
	       TD = T3 + T6;
	       TP = TN - TO;
	       T1U = T1Q - T1T;
	       T2l = T1Q + T1T;
	       T1d = TO + TN;
	  }
	  {
	       E Tp, T1o, T13, T2u, Ts, T14, T1r, T2v, Tw, T1t, T18, T2x, Tz, T19, T1w;
	       E T2y;
	       {
		    E Tn, To, T11, T12;
		    Tn = ri[WS(is, 8)];
		    To = ri[WS(is, 18)];
		    Tp = Tn + To;
		    T1o = Tn - To;
		    T11 = ii[WS(is, 8)];
		    T12 = ii[WS(is, 18)];
		    T13 = T11 - T12;
		    T2u = T11 + T12;
	       }
	       {
		    E Tq, Tr, T1p, T1q;
		    Tq = ri[WS(is, 13)];
		    Tr = ri[WS(is, 3)];
		    Ts = Tq + Tr;
		    T14 = Tq - Tr;
		    T1p = ii[WS(is, 13)];
		    T1q = ii[WS(is, 3)];
		    T1r = T1p - T1q;
		    T2v = T1p + T1q;
	       }
	       {
		    E Tu, Tv, T16, T17;
		    Tu = ri[WS(is, 12)];
		    Tv = ri[WS(is, 2)];
		    Tw = Tu + Tv;
		    T1t = Tu - Tv;
		    T16 = ii[WS(is, 12)];
		    T17 = ii[WS(is, 2)];
		    T18 = T16 - T17;
		    T2x = T16 + T17;
	       }
	       {
		    E Tx, Ty, T1u, T1v;
		    Tx = ri[WS(is, 17)];
		    Ty = ri[WS(is, 7)];
		    Tz = Tx + Ty;
		    T19 = Tx - Ty;
		    T1u = ii[WS(is, 17)];
		    T1v = ii[WS(is, 7)];
		    T1w = T1u - T1v;
		    T2y = T1u + T1v;
	       }
	       Tt = Tp - Ts;
	       TA = Tw - Tz;
	       TB = Tt + TA;
	       T2w = T2u - T2v;
	       T2z = T2x - T2y;
	       T2S = T2w + T2z;
	       T35 = T2u + T2v;
	       T36 = T2x + T2y;
	       T3f = T35 + T36;
	       TH = Tp + Ts;
	       TI = Tw + Tz;
	       TJ = TH + TI;
	       T15 = T13 - T14;
	       T1a = T18 - T19;
	       T1b = T15 + T1a;
	       T1s = T1o - T1r;
	       T1x = T1t - T1w;
	       T1W = T1s + T1x;
	       T29 = T1o + T1r;
	       T2a = T1t + T1w;
	       T2j = T29 + T2a;
	       T1h = T14 + T13;
	       T1i = T19 + T18;
	       T1j = T1h + T1i;
	  }
	  {
	       E Ta, T1z, TS, T2B, Td, TT, T1C, T2C, Th, T1E, TX, T2E, Tk, TY, T1H;
	       E T2F;
	       {
		    E T8, T9, TQ, TR;
		    T8 = ri[WS(is, 4)];
		    T9 = ri[WS(is, 14)];
		    Ta = T8 + T9;
		    T1z = T8 - T9;
		    TQ = ii[WS(is, 4)];
		    TR = ii[WS(is, 14)];
		    TS = TQ - TR;
		    T2B = TQ + TR;
	       }
	       {
		    E Tb, Tc, T1A, T1B;
		    Tb = ri[WS(is, 9)];
		    Tc = ri[WS(is, 19)];
		    Td = Tb + Tc;
		    TT = Tb - Tc;
		    T1A = ii[WS(is, 9)];
		    T1B = ii[WS(is, 19)];
		    T1C = T1A - T1B;
		    T2C = T1A + T1B;
	       }
	       {
		    E Tf, Tg, TV, TW;
		    Tf = ri[WS(is, 16)];
		    Tg = ri[WS(is, 6)];
		    Th = Tf + Tg;
		    T1E = Tf - Tg;
		    TV = ii[WS(is, 16)];
		    TW = ii[WS(is, 6)];
		    TX = TV - TW;
		    T2E = TV + TW;
	       }
	       {
		    E Ti, Tj, T1F, T1G;
		    Ti = ri[WS(is, 1)];
		    Tj = ri[WS(is, 11)];
		    Tk = Ti + Tj;
		    TY = Ti - Tj;
		    T1F = ii[WS(is, 1)];
		    T1G = ii[WS(is, 11)];
		    T1H = T1F - T1G;
		    T2F = T1F + T1G;
	       }
	       Te = Ta - Td;
	       Tl = Th - Tk;
	       Tm = Te + Tl;
	       T2D = T2B - T2C;
	       T2G = T2E - T2F;
	       T2R = T2D + T2G;
	       T32 = T2B + T2C;
	       T33 = T2E + T2F;
	       T3e = T32 + T33;
	       TE = Ta + Td;
	       TF = Th + Tk;
	       TG = TE + TF;
	       TU = TS - TT;
	       TZ = TX - TY;
	       T10 = TU + TZ;
	       T1D = T1z - T1C;
	       T1I = T1E - T1H;
	       T1V = T1D + T1I;
	       T26 = T1z + T1C;
	       T27 = T1E + T1H;
	       T2i = T26 + T27;
	       T1e = TT + TS;
	       T1f = TY + TX;
	       T1g = T1e + T1f;
	  }
	  {
	       E T2s, TC, T2r, T2I, T2K, T2A, T2H, T2J, T2t;
	       T2s = KP559016994 * (Tm - TB);
	       TC = Tm + TB;
	       T2r = FNMS(KP250000000, TC, T7);
	       T2A = T2w - T2z;
	       T2H = T2D - T2G;
	       T2I = FNMS(KP587785252, T2H, KP951056516 * T2A);
	       T2K = FMA(KP951056516, T2H, KP587785252 * T2A);
	       ro[WS(os, 10)] = T7 + TC;
	       T2J = T2s + T2r;
	       ro[WS(os, 14)] = T2J - T2K;
	       ro[WS(os, 6)] = T2J + T2K;
	       T2t = T2r - T2s;
	       ro[WS(os, 2)] = T2t - T2I;
	       ro[WS(os, 18)] = T2t + T2I;
	  }
	  {
	       E T2V, T2T, T2U, T2N, T2Y, T2L, T2M, T2X, T2W;
	       T2V = KP559016994 * (T2R - T2S);
	       T2T = T2R + T2S;
	       T2U = FNMS(KP250000000, T2T, T2Q);
	       T2L = Tt - TA;
	       T2M = Te - Tl;
	       T2N = FNMS(KP587785252, T2M, KP951056516 * T2L);
	       T2Y = FMA(KP951056516, T2M, KP587785252 * T2L);
	       io[WS(os, 10)] = T2Q + T2T;
	       T2X = T2V + T2U;
	       io[WS(os, 6)] = T2X - T2Y;
	       io[WS(os, 14)] = T2Y + T2X;
	       T2W = T2U - T2V;
	       io[WS(os, 2)] = T2N + T2W;
	       io[WS(os, 18)] = T2W - T2N;
	  }
	  {
	       E T2Z, TK, T30, T38, T3a, T34, T37, T39, T31;
	       T2Z = KP559016994 * (TG - TJ);
	       TK = TG + TJ;
	       T30 = FNMS(KP250000000, TK, TD);
	       T34 = T32 - T33;
	       T37 = T35 - T36;
	       T38 = FMA(KP951056516, T34, KP587785252 * T37);
	       T3a = FNMS(KP587785252, T34, KP951056516 * T37);
	       ro[0] = TD + TK;
	       T39 = T30 - T2Z;
	       ro[WS(os, 12)] = T39 - T3a;
	       ro[WS(os, 8)] = T39 + T3a;
	       T31 = T2Z + T30;
	       ro[WS(os, 4)] = T31 - T38;
	       ro[WS(os, 16)] = T31 + T38;
	  }
	  {
	       E T3g, T3i, T3j, T3d, T3m, T3b, T3c, T3l, T3k;
	       T3g = KP559016994 * (T3e - T3f);
	       T3i = T3e + T3f;
	       T3j = FNMS(KP250000000, T3i, T3h);
	       T3b = TE - TF;
	       T3c = TH - TI;
	       T3d = FMA(KP951056516, T3b, KP587785252 * T3c);
	       T3m = FNMS(KP587785252, T3b, KP951056516 * T3c);
	       io[0] = T3h + T3i;
	       T3l = T3j - T3g;
	       io[WS(os, 8)] = T3l - T3m;
	       io[WS(os, 12)] = T3m + T3l;
	       T3k = T3g + T3j;
	       io[WS(os, 4)] = T3d + T3k;
	       io[WS(os, 16)] = T3k - T3d;
	  }
	  {
	       E T23, T1c, T24, T2c, T2e, T28, T2b, T2d, T25;
	       T23 = KP559016994 * (T10 - T1b);
	       T1c = T10 + T1b;
	       T24 = FNMS(KP250000000, T1c, TP);
	       T28 = T26 - T27;
	       T2b = T29 - T2a;
	       T2c = FMA(KP951056516, T28, KP587785252 * T2b);
	       T2e = FNMS(KP587785252, T28, KP951056516 * T2b);
	       io[WS(os, 5)] = TP + T1c;
	       T2d = T24 - T23;
	       io[WS(os, 13)] = T2d - T2e;
	       io[WS(os, 17)] = T2d + T2e;
	       T25 = T23 + T24;
	       io[WS(os, 1)] = T25 - T2c;
	       io[WS(os, 9)] = T25 + T2c;
	  }
	  {
	       E T2k, T2m, T2n, T2h, T2p, T2f, T2g, T2q, T2o;
	       T2k = KP559016994 * (T2i - T2j);
	       T2m = T2i + T2j;
	       T2n = FNMS(KP250000000, T2m, T2l);
	       T2f = TU - TZ;
	       T2g = T15 - T1a;
	       T2h = FMA(KP951056516, T2f, KP587785252 * T2g);
	       T2p = FNMS(KP587785252, T2f, KP951056516 * T2g);
	       ro[WS(os, 5)] = T2l + T2m;
	       T2q = T2n - T2k;
	       ro[WS(os, 13)] = T2p + T2q;
	       ro[WS(os, 17)] = T2q - T2p;
	       T2o = T2k + T2n;
	       ro[WS(os, 1)] = T2h + T2o;
	       ro[WS(os, 9)] = T2o - T2h;
	  }
	  {
	       E T1m, T1k, T1l, T1K, T1M, T1y, T1J, T1L, T1n;
	       T1m = KP559016994 * (T1g - T1j);
	       T1k = T1g + T1j;
	       T1l = FNMS(KP250000000, T1k, T1d);
	       T1y = T1s - T1x;
	       T1J = T1D - T1I;
	       T1K = FNMS(KP587785252, T1J, KP951056516 * T1y);
	       T1M = FMA(KP951056516, T1J, KP587785252 * T1y);
	       io[WS(os, 15)] = T1d + T1k;
	       T1L = T1m + T1l;
	       io[WS(os, 11)] = T1L - T1M;
	       io[WS(os, 19)] = T1L + T1M;
	       T1n = T1l - T1m;
	       io[WS(os, 3)] = T1n - T1K;
	       io[WS(os, 7)] = T1n + T1K;
	  }
	  {
	       E T1Z, T1X, T1Y, T1P, T21, T1N, T1O, T22, T20;
	       T1Z = KP559016994 * (T1V - T1W);
	       T1X = T1V + T1W;
	       T1Y = FNMS(KP250000000, T1X, T1U);
	       T1N = T1h - T1i;
	       T1O = T1e - T1f;
	       T1P = FNMS(KP587785252, T1O, KP951056516 * T1N);
	       T21 = FMA(KP951056516, T1O, KP587785252 * T1N);
	       ro[WS(os, 15)] = T1U + T1X;
	       T22 = T1Z + T1Y;
	       ro[WS(os, 11)] = T21 + T22;
	       ro[WS(os, 19)] = T22 - T21;
	       T20 = T1Y - T1Z;
	       ro[WS(os, 3)] = T1P + T20;
	       ro[WS(os, 7)] = T20 - T1P;
	  }
     }
}

static const kdft_desc desc = { 20, "n1_20", {184, 24, 24, 0}, &GENUS, 0, 0, 0, 0 };

void X(codelet_n1_20) (planner *p) {
     X(kdft_register) (p, n1_20, &desc);
}

#endif				/* HAVE_FMA */
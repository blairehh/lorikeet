module Lorikeet.Base

open Lorikeet.Core

type DefaultTract() as tract =
    inherit Tract()
    let invokeProcedure procedure =
        match procedure with
            Fun func -> func tract
    override this.invoke(cell: Cell<'A>): 'A =
        match cell with
            Procedure procedure -> invokeProcedure procedure
      
module Lorikeet.Base

open Lorikeet.Core

type DefaultTract() as this =
    inherit Tract()
    let invokeEdict edict = match edict with
        Fun func -> func this
    override this.invoke(cell: Cell<'A>): 'A = match cell with
        Edict edict -> invokeEdict edict
      
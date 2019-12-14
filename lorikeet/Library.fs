namespace Lorikeet

module Core =
    [<AbstractClass>]
    type Tract() =
       abstract member invoke: Cell<'a> -> 'a
       static member (<!) (tract: Tract, cell: Cell<'a>): 'a = tract.invoke(cell)
    and Cell<'a> =
        Edict of Edict<'a>
    and Edict<'a> =
        Fun of (Tract -> 'a)
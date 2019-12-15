namespace Lorikeet

module Core =
    [<AbstractClass>]
    type Tract() =
       abstract member invoke: Cell<'A> -> 'A
       static member (<!) (tract: Tract, cell: Cell<'A>): 'A = tract.invoke(cell)
    and Cell<'A> =
        Procedure of Procedure<'A>
    and Procedure<'A> =
        Fun of (Tract -> 'A)
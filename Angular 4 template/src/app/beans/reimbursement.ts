export class Reimbursement {
    id: number;
    amount: number;
    dateSubmitted: Date;
    dateResolved: Date;
    description: string;
    receipt: any;
    authorId: number;
    resolverId: number;
    statusId: number;
    typeId: number;

    // constructor() {
    //     this.id = 1;
    //     this.amount = 0;
    //     this.dateSubmitted = new Date();
    //     this.dateResolved = null;
    //     this.description = null;
    //     this.receipt = null;
    //     this.authorId = 1;
    //     this.resolverId = 1;
    //     this.statusId = 1;
    //     this.typeId = 1;
    // }
}

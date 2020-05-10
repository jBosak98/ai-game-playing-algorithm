package lib.position


fun Position.isCorrect(): Boolean =
    this.column in 0..7 && this.row in 0..7

import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

//Validador de requisitos según el estado de un juego
export const gameStatusValidator: ValidatorFn = (form: AbstractControl): ValidationErrors | null => {
    //Obtenemos el estado
    const status = form.get('gameStatus')?.value;

    if (!status) return null;

    const rating = form.get('rating')?.value;
    const hoursPlayed = form.get('hoursPlayed')?.value;
    const startedAt = form.get('startedAt')?.value;
    const completedAt = form.get('completedAt')?.value;

    const errors: ValidationErrors = {};

    // Si está completado necesita toda la información
    if (status === 'COMPLETED') {
        //Si algo null --> error
        if (!rating) errors['ratingRequired'] = true;
        if (!hoursPlayed) errors['hoursRequired'] = true;
        if (!startedAt) errors['startedAtRequired'] = true;
        if (!completedAt) errors['completedAtRequired'] = true;
    }

    // Si está jugando necesita solo fecha de inicio y horas jugadas
    if (status === 'PLAYING') {
        if (!hoursPlayed) errors['hoursRequired'] = true;
        if (!startedAt) errors['startedAtRequired'] = true;
        if (completedAt) errors['completedNotExpected'] = true;
    }

    // Si está pausado o abandonado necesita solo fecha de inicio
    if (status === 'PAUSED' || status === 'DROPPED') {
        if (!startedAt) errors['startedAtRequired'] = true;
        if (completedAt) errors['completedNotExpected'] = true;
    }

    if (status === 'PLAN_TO_PLAY') {
        if (rating) errors['ratingNotExpected'] = true;
        if (hoursPlayed) errors['hoursNotExpected'] = true;
        if (startedAt) errors['startedNotExpected'] = true;
        if (completedAt) errors['completedNotExpected'] = true;
    }

    //Si hay error me lo devuelve, sino null
    return Object.keys(errors).length > 0 ? errors : null;
};
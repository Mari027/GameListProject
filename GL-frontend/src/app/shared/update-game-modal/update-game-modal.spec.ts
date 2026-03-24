import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateGameModal } from './update-game-modal';

describe('UpdateGameModal', () => {
  let component: UpdateGameModal;
  let fixture: ComponentFixture<UpdateGameModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateGameModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateGameModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

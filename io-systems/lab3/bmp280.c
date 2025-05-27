#include <stdio.h>

#define BMP280_S32_t  long signed int

static unsigned short dig_T1=0x6F4C;
static signed short dig_T2=0x67D3;
static signed short dig_T3=0xFC18;

BMP280_S32_t t_fine;

// Source: https://cdn-shop.adafruit.com/datasheets/BST-BMP280-DS001-11.pdf
//
// Returns temperature in DegC, resolution is 0.01 DegC. Output value of “5123” equals 51.23 DegC.
// t_fine carries fine temperature as global value
BMP280_S32_t bmp280_compensate_T_int32(BMP280_S32_t adc_T) {
    BMP280_S32_t var1, var2, T;
    var1 = ((((adc_T>>3) - ((BMP280_S32_t)dig_T1<<1))) * ((BMP280_S32_t)dig_T2)) >> 11;
    var2 = (((((adc_T>>4) - ((BMP280_S32_t)dig_T1)) * ((adc_T>>4) - ((BMP280_S32_t)dig_T1))) >> 12) * ((BMP280_S32_t)dig_T3)) >> 14;
    t_fine = var1 + var2;
    T = (t_fine * 5 + 128) >> 8;
    return T;
}

int main(void){
    // Don't forget to divide by 100 (see the comment above).
    printf("%ld\n", bmp280_compensate_T_int32(0x825c8) / 100);
}
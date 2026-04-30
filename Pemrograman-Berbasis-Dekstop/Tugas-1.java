import java.util.Scanner;

class Main {

    // ===== Data Menu (menggunakan Array, dikelompokkan per kategori) =====
    static String[] namaMakanan  = { "Nasi Pecel", "Soto Ayam", "Soto Daging", "Ayam Bakar" };
    static int[]    hargaMakanan = { 15000,        20000,       25000,         25000        };

    static String[] namaMinuman  = { "Air Mineral", "Teh Panas", "Kopi", "Es Teh Manis" };
    static int[]    hargaMinuman = { 5000,          6000,        8000,   8000           };

    // ===== Penampung Pesanan (maksimal 4 menu) =====
    static String[] pesananNama     = new String[4];
    static int[]    pesananHarga    = new int[4];
    static int[]    pesananJumlah   = new int[4];
    static String[] pesananKategori = new String[4];
    static int      jumlahPesanan   = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        tampilkanMenu();

        System.out.println();
        System.out.println("=========================================================");
        System.out.println("            INPUT PESANAN (Maksimal 4 Menu)              ");
        System.out.println("=========================================================");
        System.out.println("Ketik 0 pada nomor menu jika ingin melewati slot pesanan.");
        System.out.println();

        // Tanpa pengulangan: input dilakukan 4 kali secara sekuensial
        inputPesanan(sc, 1);
        inputPesanan(sc, 2);
        inputPesanan(sc, 3);
        inputPesanan(sc, 4);

        cetakStruk();
        sc.close();
    }

    // ===== Menampilkan Daftar Menu (dikelompokkan per kategori) =====
    static void tampilkanMenu() {
        System.out.println("=========================================================");
        System.out.println("              DAFTAR MENU RESTORAN SEDERHANA             ");
        System.out.println("=========================================================");

        System.out.println(">> KATEGORI MAKANAN <<");
        System.out.println("  1. " + padKanan(namaMakanan[0], 18) + " Rp " + hargaMakanan[0]);
        System.out.println("  2. " + padKanan(namaMakanan[1], 18) + " Rp " + hargaMakanan[1]);
        System.out.println("  3. " + padKanan(namaMakanan[2], 18) + " Rp " + hargaMakanan[2]);
        System.out.println("  4. " + padKanan(namaMakanan[3], 18) + " Rp " + hargaMakanan[3]);

        System.out.println();
        System.out.println(">> KATEGORI MINUMAN <<");
        System.out.println("  5. " + padKanan(namaMinuman[0], 18) + " Rp " + hargaMinuman[0]);
        System.out.println("  6. " + padKanan(namaMinuman[1], 18) + " Rp " + hargaMinuman[1]);
        System.out.println("  7. " + padKanan(namaMinuman[2], 18) + " Rp " + hargaMinuman[2]);
        System.out.println("  8. " + padKanan(namaMinuman[3], 18) + " Rp " + hargaMinuman[3]);
        System.out.println("=========================================================");
    }

    // ===== Input satu pesanan (struktur keputusan, tanpa loop) =====
    static void inputPesanan(Scanner sc, int slot) {
        System.out.print("Pesanan ke-" + slot + " | Pilih nomor menu (1-8, 0=lewati): ");
        int pilih = sc.nextInt();

        if (pilih == 0) {
            System.out.println("  -> Slot dilewati.");
            return;
        }
        if (pilih < 1 || pilih > 8) {
            System.out.println("  -> Pilihan tidak valid, slot dilewati.");
            return;
        }

        System.out.print("Pesanan ke-" + slot + " | Masukkan jumlah         : ");
        int jml = sc.nextInt();
        if (jml <= 0) {
            System.out.println("  -> Jumlah tidak valid, slot dilewati.");
            return;
        }

        String nama;
        int    harga;
        String kategori;

        if (pilih <= 4) {
            nama     = namaMakanan[pilih - 1];
            harga    = hargaMakanan[pilih - 1];
            kategori = "Makanan";
        } else {
            nama     = namaMinuman[pilih - 5];
            harga    = hargaMinuman[pilih - 5];
            kategori = "Minuman";
        }

        pesananNama[jumlahPesanan]     = nama;
        pesananHarga[jumlahPesanan]    = harga;
        pesananJumlah[jumlahPesanan]   = jml;
        pesananKategori[jumlahPesanan] = kategori;
        jumlahPesanan++;
    }

    // ===== Cetak Struk Pesanan =====
    static void cetakStruk() {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("                 STRUK PESANAN RESTORAN                  ");
        System.out.println("=========================================================");

        if (jumlahPesanan == 0) {
            System.out.println("Tidak ada pesanan yang dilakukan. Terima kasih!");
            System.out.println("=========================================================");
            return;
        }

        System.out.println(padKanan("Menu", 16) + padKanan("Qty", 6)
                + padKanan("Harga", 12) + "Subtotal");
        System.out.println("---------------------------------------------------------");

        int total = 0;

        // Cetak baris pesanan secara berurutan tanpa pengulangan (maks. 4)
        if (jumlahPesanan >= 1) total += cetakBaris(0);
        if (jumlahPesanan >= 2) total += cetakBaris(1);
        if (jumlahPesanan >= 3) total += cetakBaris(2);
        if (jumlahPesanan >= 4) total += cetakBaris(3);

        System.out.println("---------------------------------------------------------");
        System.out.println(padKanan("Total Biaya Pesanan", 34) + ": Rp " + total);

        // Pajak 10% dan biaya pelayanan Rp 20.000
        int pajak    = (int) Math.round(total * 0.10);
        int layanan  = 20000;
        int subtotal = total + pajak + layanan;

        System.out.println(padKanan("Pajak (10%)", 34)        + ": Rp " + pajak);
        System.out.println(padKanan("Biaya Pelayanan", 34)    + ": Rp " + layanan);
        System.out.println(padKanan("Subtotal Setelah Pajak", 34) + ": Rp " + subtotal);

        // ===== Diskon & Promo (struktur keputusan) =====
        int    diskon       = 0;
        int    potonganBogo = 0;
        String infoBogo     = "-";

        if (total > 100000) {
            diskon = (int) Math.round(subtotal * 0.10);
            System.out.println(padKanan("Diskon 10% (Total > Rp 100.000)", 34)
                    + ": Rp " + diskon);
        }

        if (total > 50000) {
            int idxMinuman = cariIndexMinuman();
            if (idxMinuman != -1) {
                potonganBogo = pesananHarga[idxMinuman];
                infoBogo     = "Beli 1 Gratis 1: " + pesananNama[idxMinuman];
                System.out.println(padKanan(infoBogo, 34) + ": Rp " + potonganBogo);
            } else {
                System.out.println("Promo Beli 1 Gratis 1 minuman tersedia,");
                System.out.println("namun tidak ada minuman pada pesanan Anda.");
            }
        }

        int totalAkhir = subtotal - diskon - potonganBogo;
        if (totalAkhir < 0) totalAkhir = 0;

        System.out.println("---------------------------------------------------------");
        System.out.println(padKanan("TOTAL PEMBAYARAN", 34) + ": Rp " + totalAkhir);
        System.out.println("=========================================================");
        System.out.println("        Terima kasih atas kunjungan Anda :)              ");
        System.out.println("=========================================================");
    }

    // Cetak satu baris pesanan, mengembalikan subtotal baris
    static int cetakBaris(int i) {
        int sub = pesananHarga[i] * pesananJumlah[i];
        System.out.println(
                padKanan(pesananNama[i], 16)
              + padKanan(String.valueOf(pesananJumlah[i]), 6)
              + padKanan("Rp " + pesananHarga[i], 12)
              + "Rp " + sub
        );
        return sub;
    }

    // Cari index minuman pertama pada pesanan (tanpa loop)
    static int cariIndexMinuman() {
        if (jumlahPesanan >= 1 && "Minuman".equals(pesananKategori[0])) return 0;
        if (jumlahPesanan >= 2 && "Minuman".equals(pesananKategori[1])) return 1;
        if (jumlahPesanan >= 3 && "Minuman".equals(pesananKategori[2])) return 2;
        if (jumlahPesanan >= 4 && "Minuman".equals(pesananKategori[3])) return 3;
        return -1;
    }

    // Util sederhana untuk meratakan kolom (memakai String.format, bukan loop)
    static String padKanan(String teks, int lebar) {
        return String.format("%-" + lebar + "s", teks);
    }
}

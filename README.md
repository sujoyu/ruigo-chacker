# 類語チェッカー

近隣の名詞、動詞に類語が使用されていないかチェックするワードマクロです。

限られた字数で文章を表現したい場合に、近隣の類語の重複を省くことで、効率的に書くことができます。

ダウンロード: https://github.com/sujoyu/ruigo-checker/releases

# 使い方

## A. 共通

1. https://github.com/sujoyu/ruigo-checker/releases  
    から`ruigo_checker.zip`をダウンロードしてきて、適当な場所に展開
1. Javaをインストール( https://www.java.com/ja/ )
2. Wordを開く
3. ファイル -> オプション -> リボンのユーザー設定
4. 右下側のボックスの`開発`にチェックを付ける

## B. 新規文書を作る場合

6. `類語チェッカー.dotm`をダブルクリック
6. 警告が出るが、`コンテンツを有効化`
7. `開発` -> `マクロ` -> `RuigoChecker`
8. ハイライトを消したいときは`ClearHighlight`

## C. 既存文書にマクロを追加する場合

6. 既存の文書を開く
6. `開発` -> `文書テンプレート` -> `テンプレート` -> `文書の作成に使用するテンプレート`  
    の、`添付(A)...`から、`類語チェッカー.dotm`を設定 -> `OK`
6. 警告が出るが、`コンテンツを有効化`
7. `開発` -> `マクロ` -> `RuigoChecker`
8. ハイライトを消したいときは`ClearHighlight`


# ruigo-checker(.jar)
近くにある類語をチェックするよ。コンソールアプリだよ。

# Usage
```
java -jar ruigo_checker-assembly-1.0.jar <text> <span>
java -jar ruigo_checker-assembly-1.0.jar "銀座でお寿司と手巻き寿司と卵焼きを食べたい。" 3
```

# Output
```
# position,length,id
4,2,0
10,2,0
```
